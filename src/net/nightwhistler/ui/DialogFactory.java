/*
 * Copyright (C) 2012 Alex Kuiper
 * 
 * This file is part of PageTurner
 *
 * PageTurner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PageTurner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PageTurner.  If not, see <http://www.gnu.org/licenses/>.*
 */
package net.nightwhistler.ui;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import net.nightwhistler.pageturner.PageTurner;
import net.nightwhistler.pageturner.R;
import net.nightwhistler.pageturner.activity.CatalogActivity;
import net.nightwhistler.pageturner.activity.PageTurnerActivity;

public class DialogFactory {

    @Inject
    private Context context;

    public static interface SearchCallBack {
        void performSearch(String query);
    }

    public void showSearchDialog( int titleId, int questionId, final SearchCallBack callBack ) {

        final AlertDialog.Builder searchInputDialogBuilder = new AlertDialog.Builder(context);

        searchInputDialogBuilder.setTitle(titleId);
        searchInputDialogBuilder.setMessage(questionId);

        // Set an EditText view to get user input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        searchInputDialogBuilder.setView(input);

        searchInputDialogBuilder.setPositiveButton(android.R.string.search_go,
                (dialog, which) -> callBack.performSearch(input.getText().toString()) );

        searchInputDialogBuilder.setNegativeButton(android.R.string.cancel,
                (dialog, which) -> {} );

        final AlertDialog searchInputDialog = searchInputDialogBuilder.show();

        input.setOnEditorActionListener( (v, actionId, event) -> {
            if (event == null) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    callBack.performSearch(input.getText().toString());
                    searchInputDialog.dismiss();
                    return true;
                }
            } else if (actionId == EditorInfo.IME_NULL) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    callBack.performSearch(input.getText().toString());
                    searchInputDialog.dismiss();
                }

                return true;
            }

            return false;

        });
    }

	public AlertDialog buildAboutDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.about);
		builder.setIcon(R.drawable.page_turner);
        builder.setMessage(context.getString(R.string.about_gpl));
        // ACERCA DE
		String version = "";
		try {
			version = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// Huh? Really?
		}

		//String html = "<h2>" + context.getString(R.string.app_name) + " </h2>";
		//html += context.getString(R.string.about_gpl);
    	//html += "<br/><a href="+"mailto:info@ebookspatagonia.com?subject=info"+">"+context.getString(R.string.contact)+"</a>";

		//builder.setMessage( Html.fromHtml(html));
        builder.setPositiveButton(context.getString(R.string.contact),new DialogInterface.OnClickListener(){
            @Override public void onClick(    DialogInterface dialog,    int which){
                sendMail();

            }});



        builder.setNegativeButton(context.getString(android.R.string.ok),null);
		//builder.setNeutralButton(context.getString(android.R.string.ok),
          //      (dialog, which) -> dialog.dismiss() );

        return builder.create();
	}

    protected void sendMail() {
        Log.i("Javier", "");

        String[] TO = {"javneira@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Enviando Mail...."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "No hay cliente de correo electrónico instalado.", Toast.LENGTH_SHORT).show();

        }

    }

}
