package br.com.tag.mobile.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.tag.mobile.model.Itens_Compra;
import br.com.tag.mobile.model.ShopCartItem;

public class DataBaseHandler extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 8;
	private static final String DATABASE_NAME = "ManacaMobile";
	public static final String TABLE_COMPRA = "Compra";
	private static final String TABLE_ITENS = "Itens";
	private static final String TABLE_PRODUTO = "Produto";
	private static final String TABLE_ATUALIZACAO = "Atualizacao";
	// Table Compra
	public static final String KEY_ID_COMPRA = "_ID";
	public static final String COLUMN_DATA_COMPRA = "data_compra";
	public static final String COLUMN_FOI_ENVIADA = "foi_enviada";
	// Table Produto
	private static final String KEY_ID_PRODUTO = "_ID";
	private static final String COLUMN_NOME = "nome";
	private static final String COLUMN_TIPO = "tipo";
	private static final String COLUMN_IMAGEM = "imagem";
	// Table Itens
	private static final String KEY_ID_ITENS = "_ID";
	private static final String UNIQUE_COLUMN_COMPRA_ITENS = "id_compra";
	private static final String UNIQUE_COLUMN_PRODUTO_ITENS = "id_produto";
	private static final String COLUMN_QUANTIDADE = "quantidade";
	private static final String COLUMN_VALOR_ITEM = "valor_item";

	public DataBaseHandler( Context context )
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String query;
		query = "CREATE TABLE " + TABLE_COMPRA + 
				"( " + KEY_ID_COMPRA + " INTEGER PRIMARY KEY, " +
				COLUMN_DATA_COMPRA + " TEXT, " + 
				COLUMN_FOI_ENVIADA + " INTEGER)";
		db.execSQL(query);
		query = "CREATE TABLE " + TABLE_PRODUTO + 
				"( " + KEY_ID_PRODUTO + " INTEGER PRIMARY KEY, " +
				COLUMN_NOME + " TEXT, " + COLUMN_TIPO + " TEXT, " + 
				COLUMN_IMAGEM + " TEXT)";
		db.execSQL(query);
		query = "CREATE TABLE " + TABLE_ITENS + 
				"( " + KEY_ID_ITENS + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				UNIQUE_COLUMN_COMPRA_ITENS + " INTEGER, " + UNIQUE_COLUMN_PRODUTO_ITENS + 
				" INTEGER, " + COLUMN_QUANTIDADE + " INTEGER, " + COLUMN_VALOR_ITEM + 
				" REAL, UNIQUE(" + UNIQUE_COLUMN_COMPRA_ITENS + ", " + 
				UNIQUE_COLUMN_PRODUTO_ITENS + ")" + " " + 
				"FOREIGN KEY(" + UNIQUE_COLUMN_COMPRA_ITENS + ") REFERENCES " + 
				TABLE_COMPRA + "(" + KEY_ID_COMPRA + ") " + 
				"FOREIGN KEY(" + UNIQUE_COLUMN_PRODUTO_ITENS + ") REFERENCES " + 
				TABLE_PRODUTO + "(" + KEY_ID_PRODUTO + "))";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		String query;
		query = 	"DROP TABLE IF EXISTS " + TABLE_ATUALIZACAO;
		db.execSQL(query);
		query = 	"DROP TABLE IF EXISTS " + TABLE_COMPRA;
		db.execSQL(query);
		query = 	"DROP TABLE IF EXISTS " + TABLE_PRODUTO;
		db.execSQL(query);
		query = 	"DROP TABLE IF EXISTS " + TABLE_ITENS;
		db.execSQL(query);
		onCreate(db);
	}
	
	public int getIncompleteCartId ()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String query = 	"SELECT " + KEY_ID_COMPRA + " " +
						"FROM " + TABLE_COMPRA + " " +
						"WHERE " + COLUMN_FOI_ENVIADA + " = 0";
		Cursor cursor = db.rawQuery(query, null);
		if ( cursor.moveToFirst() )
		{
			int result = cursor.getInt(0);
			db.close();
			return result;
		}
		else
		{
			db.close();
			return 0;
		}
	}
	
	public int addCart ()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		Calendar date = Calendar.getInstance();
		String _date =	date.get(Calendar.DAY_OF_MONTH) + "-" +
				 		(date.get(Calendar.MONTH) + 1) + "-" + 
				 		date.get(Calendar.YEAR);
		
		ContentValues insertValues = new ContentValues();
		insertValues.put(COLUMN_DATA_COMPRA, _date);
		insertValues.put(COLUMN_FOI_ENVIADA, 0);
		
		db.insert(TABLE_COMPRA, null, insertValues);
		db.close();
		
		return this.getIncompleteCartId();
	}
	
	public void addItem (Itens_Compra item)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues insertValues = new ContentValues();
		insertValues.put(UNIQUE_COLUMN_COMPRA_ITENS, item.getIdCompra());
		insertValues.put(UNIQUE_COLUMN_PRODUTO_ITENS, item.getIdProduto());
		insertValues.put(COLUMN_QUANTIDADE, item.getQuantity());
		insertValues.put(COLUMN_VALOR_ITEM, item.getItemAmount());
		
		db.insert(TABLE_ITENS, null, insertValues);
		db.close();
	}
	
	public void addProduct ( int idProd, String name, String photo, 
							 String type )
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = 	"SELECT COUNT(" + KEY_ID_PRODUTO + ") " +
						"FROM " + TABLE_PRODUTO + " " +  
						"WHERE " + KEY_ID_PRODUTO + " = " + 
						idProd;
		
		System.out.println(query);
		
		Cursor cursor = db.rawQuery(query, null);
		
		if ( cursor.moveToFirst() )
		{
			int result = cursor.getInt(0);
			if ( result == 0 )
			{
				System.out.println("Resultado ZERO -- Correto");
				
				db = this.getWritableDatabase();
				
				ContentValues insertValues = new ContentValues();
				insertValues.put(KEY_ID_PRODUTO, idProd);
				insertValues.put(COLUMN_NOME, name);
				insertValues.put(COLUMN_TIPO, type);
				insertValues.put(COLUMN_IMAGEM, photo);
				
				db.insert(TABLE_PRODUTO, null, insertValues);
			}
		}
		db.close();
	}
	
	public ArrayList<ShopCartItem> getItens ( int purchaseId )
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = 	"SELECT i." + UNIQUE_COLUMN_PRODUTO_ITENS + ", " +
						"p." + COLUMN_NOME + ", i." + COLUMN_QUANTIDADE + ", " + 
						"i." + COLUMN_VALOR_ITEM + " " +
						"FROM " + TABLE_ITENS + " i " +
						"INNER JOIN " + TABLE_PRODUTO + " p ON i." + UNIQUE_COLUMN_PRODUTO_ITENS + 
						" = p." + KEY_ID_PRODUTO + " " + 
						"WHERE i." + UNIQUE_COLUMN_COMPRA_ITENS + " = " + purchaseId; 
		
		Cursor cursor = db.rawQuery(query, null);
		
		ArrayList<ShopCartItem> itens = new ArrayList<ShopCartItem>();
		
		if ( cursor.moveToFirst() )
		{
			do
			{
				ShopCartItem item = new ShopCartItem(cursor.getInt(0), 
													 cursor.getString(1), 
													 cursor.getInt(2), 
													 cursor.getFloat(3));
				itens.add(item);
			}
			while (cursor.moveToNext());
		}
		
		return itens;
	}
	
	public void removeItem ( int idProduct, int idPurchase )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String query =	"id_compra = ? AND id_produto = ?";
		String[] whereArgs = {String.valueOf(idPurchase), 
							  String.valueOf(idProduct)};
		db.delete(TABLE_ITENS, query, whereArgs);
		db.close();
	}
	
	public void updateQtdItem ( int idProduct, int idPurchase, int qtd)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues updateValues = new ContentValues();
		updateValues.put(COLUMN_QUANTIDADE, qtd);
		
		String query =	"id_compra = ? AND id_produto = ?";
		String[] whereArgs = {String.valueOf(idPurchase), 
							  String.valueOf(idProduct)};
		
		db.update(TABLE_ITENS, updateValues, query, whereArgs);
		db.close();
	}
	
	public void markAsSentPurchase ( int idPurchase )
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues updateValues = new ContentValues();
		updateValues.put(COLUMN_FOI_ENVIADA, 1);
		
		String query =	"_ID = ?";
		String[] whereArgs = {String.valueOf(idPurchase)};
		
		db.update(TABLE_COMPRA, updateValues, query, whereArgs);
		db.close();
	}
}