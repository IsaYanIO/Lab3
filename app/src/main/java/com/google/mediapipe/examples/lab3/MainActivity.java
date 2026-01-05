package com.google.mediapipe.examples.lab3;

import android.os.Bundle;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Вызов родительского метода - обязательная строка
        super.onCreate(savedInstanceState);

        // Включает отображение контента под системными барами (статус бар и навигационная панель)
        EdgeToEdge.enable(this);

        // Загружает XML layout - обязательная строка
        setContentView(R.layout.activity_main);

        // Обработчик для корректного отступа контента от системных баров
        // Добавляет padding, чтобы контент не перекрывался системными элементами
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
    }

    private void setupClickListeners() {
        button1.setOnClickListener(v -> showToastWithDuration("Кнопка номер 1 нажата", 2000));
        button2.setOnClickListener(v -> showToastWithDuration("Кнопка номер 2 нажата", 3500));
        button3.setOnClickListener(v -> showButtonTextColorChangeDialog());
        button4.setOnClickListener(v -> showAnimalSelectionDialog());
    }

    private void showToastWithDuration(String message, int duration) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
        handler.postDelayed(toast::cancel, duration);
    }

    private void showButtonTextColorChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Кнопка 3")
                .setMessage("Изменить цвет текста у кнопок?")
                .setIcon(R.drawable.test_icon)
                .setPositiveButton("Да", (dialog, which) -> {
                    changeButtonsTextColor(Color.RED);
                    dialog.dismiss();
                })
                .setNegativeButton("Отмена", (dialog, which) -> {
                    Toast.makeText(MainActivity.this, "Диалог закрыт", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeButtonsTextColor(int color) {
        button1.setTextColor(color);
        button2.setTextColor(color);
        button3.setTextColor(color);
        button4.setTextColor(color);
    }

    private void showAnimalSelectionDialog() {
        final String[] animals = {"Лошадь", "Ягуар", "Леопард", "Осел", "Акула", "Волк", "Лось"};
        final boolean[] checkedItems = {false, false, false, false, false, false, false};
        final boolean[] correctAnswers = {true, false, false, true, false, false, true};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Какие из животных травоядные?")
                .setMultiChoiceItems(animals, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked)
                .setPositiveButton("Проверить", (dialog, which) -> checkAnimalSelection(checkedItems, correctAnswers))
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkAnimalSelection(boolean[] selected, boolean[] correct) {
        boolean isCorrect = true;

        for (int i = 0; i < selected.length; i++) {
            if (selected[i] != correct[i]) {
                isCorrect = false;
                break;
            }
        }

        if (isCorrect) {
            Toast.makeText(this, "Правильный ответ!", Toast.LENGTH_LONG).show();
        } else {
            hideAllButtons();
            Toast.makeText(this, "Неправильный ответ!", Toast.LENGTH_LONG).show();
        }
    }

    private void hideAllButtons() {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
    }
}