package br.com.tag.mobile.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import br.com.tag.mobile.persistence.DataBaseHandler;

public class ManacaContentProvider extends ContentProvider
{
	// database
	private DataBaseHandler database;
	
	// Used for the UriMacher
	private static final int COMPRAS = 10;
	private static final int COMPRA_ID = 20;
	
	private static final String AUTHORITY = "br.com.tag.mobile.contentProvider";
	private static final String BASE_PATH = "products";	

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + 
													"/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + 
											  "/" + BASE_PATH;

	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + 
												   "/" + BASE_PATH;

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static
	{
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, COMPRAS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COMPRA_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType)
		{
			case COMPRAS:
				rowsDeleted = sqlDB.delete ( DataBaseHandler.TABLE_COMPRA, selection,
											 selectionArgs
											);
				break;
				
			case COMPRA_ID:
				String id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection))
				{
					rowsDeleted = sqlDB.delete ( DataBaseHandler.TABLE_COMPRA,
												 DataBaseHandler.KEY_ID_COMPRA + 
												 "=" + id, null
												);
				}
				else
				{
					rowsDeleted = sqlDB.delete ( DataBaseHandler.TABLE_COMPRA,
												 DataBaseHandler.KEY_ID_COMPRA + 
												 "=" + id + " AND " + selection,
												 selectionArgs
												);
				}
				break;
				
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0)
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		
		switch (uriType)
		{
			case COMPRAS:
				id = sqlDB.insert(DataBaseHandler.TABLE_COMPRA, null, values);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public boolean onCreate()
	{
		database = new DataBaseHandler(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		queryBuilder.setTables(DataBaseHandler.TABLE_COMPRA);

		int uriType = sURIMatcher.match(uri);
		switch (uriType)
		{
			case COMPRAS:
				break;
			case COMPRA_ID:
				queryBuilder.appendWhere ( DataBaseHandler.KEY_ID_COMPRA + 
										   "=" + uri.getLastPathSegment()
										 );
				break;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(	db, projection, selection,
											selectionArgs, null, null, 
											sortOrder);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3)
	{
		return 0;
	}
}