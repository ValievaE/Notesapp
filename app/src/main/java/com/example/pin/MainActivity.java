package com.example.pin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity implements Keystory {

    public static final String PIN = "pin";

    private ImageView i1, i2, i3, i4;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnBackspace, btnCheck;
    private ArrayList<String> pinList;
    public static SharedPreferences sharedPrefPin;
    private String enteredPin;

    byte[] salt;
    char[] password;
    int iterationCount;
    int keyLength;
    private SecretKeySpec key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClickBtn1();
        onClickBtn2();
        onClickBtn3();
        onClickBtn4();
        onClickBtn5();
        onClickBtn6();
        onClickBtn7();
        onClickBtn8();
        onClickBtn9();
        onClickBtn0();
        onClickBtnBackSpace();
        onClickBtnCheck();


    }


    private void fillCircle() {
        switch (pinList.size()) {
            case 4:
                i4.setImageResource(R.drawable.circle_2);
                break;
            case 3:
                i4.setImageResource(R.drawable.circle);
                i3.setImageResource(R.drawable.circle_2);
                break;
            case 2:
                i3.setImageResource(R.drawable.circle);
                i2.setImageResource(R.drawable.circle_2);
                break;
            case 1:
                i2.setImageResource(R.drawable.circle);
                i1.setImageResource(R.drawable.circle_2);
                break;
            default:
                i1.setImageResource(R.drawable.circle);
        }
    }

    private void cleanCircle() {
        switch (pinList.size()) {
            case 4:
                i4.setImageResource(R.drawable.circle);
                break;
            case 3:
                i3.setImageResource(R.drawable.circle);

                break;
            case 2:
                i2.setImageResource(R.drawable.circle);

                break;
            case 1:
                i1.setImageResource(R.drawable.circle);
                break;
            default:
                i1.setImageResource(R.drawable.circle);
        }
    }


    private void onClickBtn1() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_1));
                }
                fillCircle();
            }
        });

    }

    private void onClickBtn2() {

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_2));
                }
                fillCircle();
            }
        });
    }

    private void onClickBtn3() {

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_3));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn4() {

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_4));
                }
                fillCircle();
            }
        });

    }

    private void onClickBtn5() {
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_5));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn6() {
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_6));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn7() {
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_7));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn8() {
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_8));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn9() {
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_9));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtn0() {
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pinList.size() < 4) {
                    pinList.add(getResources().getString(R.string.btn_0));
                }
                fillCircle();

            }
        });
    }

    private void onClickBtnCheck() {
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = pinList.toString().replaceAll("[,\\s\\[\\]]", "");
                if (sharedPrefPin == null) {
                    savePin();
                    //Toast.makeText(MainActivity.this, pinList.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, AllNotesActivity.class);
                    startActivity(intent);
                } else if (s.equals(sharedPrefPin.getString(PIN, ""))) {
                    Intent intent = new Intent(MainActivity.this, AllNotesActivity.class);
                    startActivity(intent);
                } else if (!s.equals(sharedPrefPin.getString(PIN, ""))) {
                    Toast.makeText(MainActivity.this, "Wrong PIN!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onClickBtnBackSpace() {
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pinList.isEmpty()) {
                    cleanCircle();
                    pinList.remove(pinList.size() - 1);
                }
            }
        });
    }
    public void savePin() {
        SharedPreferences.Editor myEditor = sharedPrefPin.edit();
        myEditor.putString(PIN, enteredPin);
        myEditor.apply();

    }


    /*public void savePin() {
        SharedPreferences.Editor myEditor = sharedPrefPin.edit();

        String encryptedPassword = null;
        try {
            encryptedPassword = encrypt(enteredPin, key);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        myEditor.putString(PIN, encryptedPassword);
        myEditor.apply();

    }

    private String getDateFromSharedPref() {
        String encryptedPassword = sharedPrefPin.getString(PIN, "");
        String decryptedPassword = "";
        try {
            decryptedPassword = decrypt(encryptedPassword, key);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return decryptedPassword;
    }


    private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(enteredPin.toCharArray(), salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    private static String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }

    private static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getDecoder().decode(property);
        }
        return new byte[0];
    }*/

    private void init() {
        i1 = findViewById(R.id.imageview_circle1);
        i2 = findViewById(R.id.imageview_circle2);
        i3 = findViewById(R.id.imageview_circle3);
        i4 = findViewById(R.id.imageview_circle4);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn0 = findViewById(R.id.btn_0);
        btnBackspace = findViewById(R.id.btn_backspace);
        btnCheck = findViewById(R.id.btn_checkPin);
        pinList = new ArrayList<>();
        sharedPrefPin = getSharedPreferences("EntrPin", MODE_PRIVATE);
        enteredPin = pinList.toString();

        /*password = enteredPin.toCharArray();
        salt = ("12345678").getBytes();
        iterationCount = 40000;
        keyLength = 128;
        key = null;
        try {
            key = createSecretKey(enteredPin.toCharArray(), salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }*/

    }
}

