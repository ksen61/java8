package com.example.proekt1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView Formula, EndResult;

    private Button One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Zero;
    private char Action;

    private Button Plus, Minus, Multiple, Division, Result;
    private Button SquareRoot, Square, Percentage;

    private double valueFirst = Double.NaN;
    private double valueSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        setupListeners();
    }

    private void setupView() {
        One = findViewById(R.id.One);
        Two = findViewById(R.id.Two);
        Three = findViewById(R.id.Three);
        Four = findViewById(R.id.Four);
        Five = findViewById(R.id.Five);
        Six = findViewById(R.id.Six);
        Seven = findViewById(R.id.Seven);
        Eight = findViewById(R.id.Eight);
        Nine = findViewById(R.id.Nine);
        Zero = findViewById(R.id.Zero);
        Plus = findViewById(R.id.Plus);
        Minus = findViewById(R.id.Minus);
        Multiple = findViewById(R.id.Multiple);
        Division = findViewById(R.id.Division);
        Result = findViewById(R.id.Result);
        SquareRoot = findViewById(R.id.SquareRoot);
        Square = findViewById(R.id.Square);
        Percentage = findViewById(R.id.Percentage);
        Formula = findViewById(R.id.Formula);
        EndResult = findViewById(R.id.EndResult);
    }

    private void setupListeners() {
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Formula.append(button.getText().toString());
            }
        };
        One.setOnClickListener(numberClickListener);
        Two.setOnClickListener(numberClickListener);
        Three.setOnClickListener(numberClickListener);
        Four.setOnClickListener(numberClickListener);
        Five.setOnClickListener(numberClickListener);
        Six.setOnClickListener(numberClickListener);
        Seven.setOnClickListener(numberClickListener);
        Eight.setOnClickListener(numberClickListener);
        Nine.setOnClickListener(numberClickListener);
        Zero.setOnClickListener(numberClickListener);
        View.OnClickListener actionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                calculate();
                Action = button.getText().charAt(0);
                Formula.setText(String.valueOf(valueFirst) + Action);
                EndResult.setText(null);
            }
        };
        Plus.setOnClickListener(actionClickListener);
        Minus.setOnClickListener(actionClickListener);
        Multiple.setOnClickListener(actionClickListener);
        Division.setOnClickListener(actionClickListener);

        SquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSquareRoot();
            }
        });

        Square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSquare();
            }
        });

        Percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePercentage();
            }
        });

        Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                Action = '=';
                EndResult.setText(String.valueOf(valueFirst));
                Formula.setText(null);
            }
        });
    }

    private void calculate() {
        if (!Double.isNaN(valueFirst)) {
            String textFormula = Formula.getText().toString().trim();

            int indexAction = -1;
            char[] actions = {'+', '-', '*', '/'};
            for (char action : actions) {
                if (textFormula.lastIndexOf(action) > indexAction) {
                    indexAction = textFormula.lastIndexOf(action);
                }
            }

            if (indexAction > 0 && indexAction < textFormula.length() - 1) {
                try {
                    String number = textFormula.substring(indexAction + 1);
                    valueSecond = Double.parseDouble(number);

                    switch (textFormula.charAt(indexAction)) {
                        case '+':
                            valueFirst += valueSecond;
                            break;
                        case '-':
                            valueFirst -= valueSecond;
                            break;
                        case '*':
                            valueFirst *= valueSecond;
                            break;
                        case '/':
                            if (valueSecond == 0) {
                                throw new ArithmeticException("Деление на ноль");
                            } else {
                                valueFirst /= valueSecond;
                            }
                            break;
                    }

                    if (Double.isNaN(valueFirst)) {
                        valueFirst = 0.0;
                    }

                } catch (NumberFormatException e) {
                    showErrorToast("Ошибка: некорректный формат числа");
                    valueFirst = Double.NaN;
                } catch (ArithmeticException e) {
                    showErrorToast("Ошибка: деление на ноль");
                    valueFirst = Double.NaN;
                }
            } else {
                showErrorToast("Ошибка: некорректная операция");
                valueFirst = Double.NaN;
            }
        } else {
            try {
                valueFirst = Double.parseDouble(Formula.getText().toString().trim());
            } catch (NumberFormatException e) {
                showErrorToast("Ошибка: некорректный формат числа");
                valueFirst = Double.NaN;
            }
        }

        EndResult.setText(String.valueOf(valueFirst));
        Formula.setText("");
    }

    private void calculateSquareRoot() {
        String text = Formula.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            try {
                double number = Double.parseDouble(text);
                if (number >= 0) {
                    valueFirst = Math.sqrt(number);
                    EndResult.setText(String.valueOf(valueFirst));
                    Formula.setText("√" + text);
                } else {
                    showErrorToast("Ошибка: невозможно извлечь корень из отрицательного числа");
                }
            } catch (NumberFormatException e) {
                showErrorToast("Ошибка: некорректный формат числа");
            }
        } else {
            showErrorToast("Ошибка: введите число");
        }
    }

    private void calculateSquare() {
        String text = Formula.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            try {
                double number = Double.parseDouble(text);
                valueFirst = number * number;
                EndResult.setText(String.valueOf(valueFirst));
                Formula.setText(text + "²");
            } catch (NumberFormatException e) {
                showErrorToast("Ошибка: некорректный формат числа");
            }
        } else {
            showErrorToast("Ошибка: введите число");
        }
    }

    private void calculatePercentage() {
        String text = Formula.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            try {
                double number = Double.parseDouble(text);
                valueFirst = number / 100;
                EndResult.setText(String.valueOf(valueFirst));
                Formula.setText(text + "%");
            } catch (NumberFormatException e) {
                showErrorToast("Ошибка: некорректный формат числа");
            }
        } else {
            showErrorToast("Ошибка: введите число");
        }
    }

    private void showErrorToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClearClick(View view) {
        if (!Double.isNaN(valueFirst)) {
            valueFirst = Double.NaN;
            valueSecond = 0;
            Action = '\0';
            Formula.setText("");
            EndResult.setText("0");
        }
    }
}
