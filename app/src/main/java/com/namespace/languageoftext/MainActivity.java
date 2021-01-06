package com.namespace.languageoftext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.IdentifiedLanguage;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);


    }

   public void predictLanguage(View view){

        String text = String.valueOf(editText.getText());

       LanguageIdentifier languageIdentifier = LanguageIdentification.getClient(
               new LanguageIdentificationOptions.Builder()
                       .setConfidenceThreshold(0.50f)
                       .build());

       languageIdentifier.identifyPossibleLanguages(text)
               .addOnSuccessListener(new OnSuccessListener<List<IdentifiedLanguage>>() {
                   @Override
                   public void onSuccess(List<IdentifiedLanguage> identifiedLanguages) {
                       for (IdentifiedLanguage identifiedLanguage : identifiedLanguages) {
                           String language = identifiedLanguage.getLanguageTag();
                           float confidence = identifiedLanguage.getConfidence();
                           textView.append(" "+ language + " "+ confidence );
                           Log.i("TAG", language + " (" + confidence + ")");
                       }
                   }
               })
               .addOnFailureListener(
                       new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               // Model couldn’t be loaded or other internal error.
                               // ...
                           }
                       });

    }
}