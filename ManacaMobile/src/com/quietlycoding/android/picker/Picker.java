/*
 * Copyright 2010 QuietlyCoding <mike@quietlycoding.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quietlycoding.android.picker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import br.com.tag.mobile.handlers.AddItensToCartHandler;
import br.com.tag.mobile.httpRequest.GetProductsImageTask;
import br.com.tag.mobile.organico.ProductsListActivity;
import br.com.tag.mobile.organico.R;

public class Picker extends Activity
{
	private ImageView productsImage;
	private Button addItenToCart;
	private NumberPicker qtd;
	private int selectedProd;
	private float priceProd;
	private String imgName;
	private String prodName;
	private String typeProd;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker);
        this.productsImage = (ImageView) this.findViewById(R.id.loadImage);
        this.addItenToCart = (Button) this.findViewById(R.id.btnInserir);
        this.qtd = (NumberPicker) this.findViewById(R.id.qtdPicker);
        this.qtd.changeCurrent(1);
        this.imgName = getIntent().getStringExtra("imageName");
        this.selectedProd = getIntent().getIntExtra("productId", 0);
        this.priceProd = getIntent().getFloatExtra("productPrice", 0);
        this.prodName = getIntent().getStringExtra("productName");
        this.typeProd = getIntent().getStringExtra("productType");
        if ( ProductsListActivity.isNetworkAvaiable(this) )
		{
        	System.out.println(imgName);
			// call AsyncTask
			new GetProductsImageTask(this, imgName).execute();
		}
		else
		{
			this.productsImage.setImageResource(R.drawable.no_image);
		}
        this.addItenToCart.setOnClickListener(new AddItensToCartHandler(this));
    }

	public void setProductsImage ( Bitmap productImage )
	{
		this.productsImage.setImageBitmap(productImage);
	}
	
	public int getQtd ()
	{
		return this.qtd.mCurrent;
	}
	
	public int getSelectedProd ()
	{
		return this.selectedProd;
	}
	
	public float getPriceProd ()
	{
		return this.priceProd;
	}
	
	public String getImgName ()
	{
		return this.imgName;
	}
	
	public String getProdName ()
	{
		return this.prodName;
	}
	
	public String getTypeProduct ()
	{
		return this.typeProd;
	}
}