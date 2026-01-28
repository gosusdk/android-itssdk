package com.itc.its;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gosu.itc.its.ITSSdk;
import com.gosu.itc.its.inteface.iTSListener;
import com.gosu.itc.its.utils.ItsTrackingProperty;
import com.gosu.itc.its.utils.SdkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //
    private Animation scaleUp;
    private Animation scaleDown;
    //
    private VideoView loginBgVideoView;
    private ImageView ingameBgImageView;

    private Button btnLogin;
    private LinearLayout viewAction;
    private RelativeLayout viewLogin;
    private RelativeLayout viewCommand;
    private RelativeLayout viewLogout;

    private RelativeLayout viewITSTask;
    private Button btnLogout;

    private Button btnCommand;
    private Button btnTaskList;

    private String userId;
    private String accessToken;
    private String userName;
    private String userEmail;

    private String serverInfo;
    private String characterId;
    private String characterName;
    private String[] Servers = new String[]{"S1-Lữ Bố", "S2-Triệu Vân", "S3-Lưu Bị", "S4-Quan Vũ", "S5-Trương Phi", "S6-Tiểu Kiều", "S7-Đại Kiều", "S8-Chu Du"};
    private String[] productNames = new String[]{"Gói 60 Long Ngọc", "Gói 180 Long Ngọc", "Gói 600 Long Ngọc", "Gói 900 Long Ngọc", "Gói 1200 Long Ngọc", "Gói 1800 Long Ngọc", "Gói 3000 Long Ngọc", "Gói 6000 Long Ngọc", "Gói 9000 Long Ngọc"};
    private String[] productIDs = new String[]{"product1", "product2", "product3", "product4", "product5", "product6", "product7", "product8", "product9"};
    private String[] amounts = new String[]{"22000.0", "49000.0", "99000.0", "129000.0", "199000.0", "299000.0", "399000.0", "599000.0", "999000.0"};
    private String[] activityIds = new String[]{"a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15"};
    private String[] activityNames = new String[]{"Chiêu mộ", "Nhiệm vụ chính tuyến", "Nhiệm vụ hàng ngày", "Cống hiến Bang hội ", "Quyết thắng Trường An", "Thiên địa quần hùng", "Tam quân tranh bá", "Huyết Chiến Quan Độ", "60", "70", "80", "90", "100", "120", "150", "200"};
    //
    private String[] itemIds = new String[]{"i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8", "i9", "i10", "i11", "i12", "i13", "i14", "i15"};
    private String[] itemNames = new String[]{"EXP Đan-I", "EXP Đan-2", "Mảnh tướng ngẫu nhiên", "Đồng xu", "Thẻ chiêu mộ", "Mảnh tướng huyền thoại Lữ Bố", "Mảnh thư hùng song kiếm", "Cam lộ thảo", "Sổ cam thảo", "Hà thủ ô trăm năm", "Giới hạn tuần", "Giới hạn tháng", "Thần binh phù thạch", "Mãnh hùng song kiếm", "Lang nha bổng", "Tàng viên xích"};

    private List<UserInfo> itemList;

    private AlertDialog UserListDialog;
    private ImageView mTaskNotification;

    private ITSSdk itsSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        scaleUp = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_down);
        initView();


        // Initialize ITSSdk
        itsSdk = ITSSdk.getInstance();
        JSONObject customInfo = new JSONObject();
        try {
            customInfo.put("version", "2.0.0");
        } catch (JSONException e) {
            Log.e("ITS_TAG", "Error creating customInfo JSON:" + e);
            throw new RuntimeException(e);
        }
        itsSdk.initSdk(MainActivity.this, "", "", customInfo, new iTSListener() {
            @Override
            public void OnInitializeSuccess() {
                Log.d("ITS_TAG", "succeed");
            }

            @Override
            public void onInitializeError(SdkException exception) {
                Log.d("ITS_TAG", exception.getMessage());
            }

            // Coming soon...
            @Override
            public void onNotifyNewTask(int number) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI elements here
                        mTaskNotification.setVisibility(ViewStub.VISIBLE);
//                        if (number > 0) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                            builder.setTitle("Thông báo").setMessage(String.format("Có %d nhiệm vụ đang chờ đợi bạn", number)).setPositiveButton("OK", null).show();
//                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        // Video background
        loginBgVideoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.theme;
        Uri uri = Uri.parse(videoPath);
        loginBgVideoView.setVideoURI(uri);
        loginBgVideoView.start();
        loginBgVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                loginBgVideoView.start();
            }
        });
        //
        ingameBgImageView = findViewById(R.id.bgImageIngame);
        btnLogin = findViewById(R.id.btnLogin);
        viewLogin = findViewById(R.id.viewLogin);
        viewAction = findViewById(R.id.layoutActionPanel);
        viewCommand = findViewById(R.id.viewCommand);
        viewITSTask = findViewById(R.id.viewITSTask);
        btnTaskList = findViewById(R.id.btnITSTask);
        mTaskNotification = findViewById(R.id.ivIconNewTask);
        viewLogout = findViewById(R.id.viewLogout);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserListDialog();
            }
        });

        btnLogout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showCommandDialog(MainActivity.this);
                onLogout();
            }
        });

        mTaskNotification.setVisibility(View.GONE);
        ingameBgImageView.setVisibility(View.GONE);
        viewAction.setVisibility(View.GONE);

        loadJsonData();
    }

    private void loadJsonData() {
        try {
            // Read the JSON file from assets folder
            InputStream inputStream = getAssets().open("users.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            itemList = Arrays.asList(gson.fromJson(json, UserInfo[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showUserListDialog() {
        // Create a dialog with a ListView
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.UserDialog);
        builder.setTitle("LIST OF USERS :");
        // Inflate the layout for the dialog
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.users_dialog, null);
        builder.setView(dialogView);
        ListView listView = dialogView.findViewById(R.id.listView);
        UserAdapter userAdapter = new UserAdapter(this, R.layout.user_item_layout, itemList);
        listView.setAdapter(userAdapter);
        UserListDialog = builder.create();
        UserListDialog.setCanceledOnTouchOutside(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo clickedItem = itemList.get(position);
                onSelectUserSuccess(clickedItem.getUser_id(), clickedItem.getUser_name(), clickedItem.getUser_email(), clickedItem.getUser_fullname());
                if (UserListDialog != null && UserListDialog.isShowing()) {
                    UserListDialog.dismiss();
                }
            }
        });
        UserListDialog.show();
    }

    public void onSelectUserSuccess(String UserId, String UserName, String UserEmail, String accesstoken) {
        userId = UserId;
        userName = UserName;
        userEmail = UserEmail;
        accessToken = accesstoken;

        // Call ITS Tracking login method
        itsSdk.login(userId, UserName, UserEmail);

        showServerSelectDialog(MainActivity.this);
    }

    private void showServerSelectDialog(Context context) {
        //
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.select_server_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.TransparentDialog);
        builder.setView(dialogView);
        Spinner serverComboBox = dialogView.findViewById(R.id.combox_serverinfo);

        //
        EditText chacracterTextBox = dialogView.findViewById(R.id.textBox_characterid);
        //cache character_id
        characterId = SharedPreferencesHelper.getInstance(context).getData("character_id");
        if (characterId.isEmpty()) characterId = "charid-of-" + userId;
        chacracterTextBox.setText(characterId);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, Servers);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        serverComboBox.setAdapter(adapter);
        //cache server_info
        serverInfo = SharedPreferencesHelper.getInstance(context).getData("server_info");
        if (!serverInfo.isEmpty()) {
            int index = Arrays.asList(Servers).indexOf(serverInfo);
            if (index != -1) serverComboBox.setSelection(index);
        }
        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                serverInfo = serverComboBox.getSelectedItem().toString();
                characterId = chacracterTextBox.getText().toString();
                //save server_info and character_id in cache
                SharedPreferencesHelper.getInstance(context).saveData("server_info", serverInfo);
                SharedPreferencesHelper.getInstance(context).saveData("character_id", characterId);
                //
                characterName = "nameof" + "-->" + characterId;

                // Call ITS Tracking enter game method
                itsSdk.enterGame(userId, characterId, characterName, serverInfo);

                onLoginSuccess(userId, characterId);
                onGamePlay();
            }
        });
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onGamePlay() {
        loginBgVideoView.setVisibility(View.GONE);
        viewLogin.setVisibility(View.GONE);
        ingameBgImageView.setVisibility(View.VISIBLE);
        viewAction.setVisibility(View.VISIBLE);

        if (btnCommand == null) {
            btnCommand = findViewById(R.id.btnFastCommand);
            btnCommand.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.startAnimation(scaleDown);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.startAnimation(scaleUp);
                    }
                    return false;

                }
            });
            btnCommand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommandDialog(MainActivity.this);
                }
            });
        }

        // Call ITS Tracking start tutorial method
        itsSdk.startTutorial(userId, characterId, characterName, serverInfo);
        // Call ITS Tracking complete tutorial method
        itsSdk.completeTutorial(userId, characterId, characterName, serverInfo);
    }

    public void onLoginSuccess(String UserId, String charId) {
        //itsSdk.setNotifyListener(userId, charId);
    }

    public void onLogout() {
        viewAction.setVisibility(View.GONE);
        viewLogin.setVisibility(View.VISIBLE);
        ingameBgImageView.setVisibility(View.GONE);
        loginBgVideoView.setMediaController(null);
        loginBgVideoView.setVisibility(View.VISIBLE);
        //itsSdk.stopNotifyListener();

        // Call ITS Tracking logout method
        itsSdk.logout();

        SharedPreferencesHelper.getInstance(MainActivity.this).removeAllData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showCommandDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.command_dialog, null);

        // Create a Dialog instance instead of AlertDialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        Animation scaleUp = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Animation scaleDown = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_down);
        //
        Button btnVipUp = dialog.findViewById(R.id.btnVip_up);
        Button btnLvUp = dialog.findViewById(R.id.btnLevel_up);
        Button btnPowUp = dialog.findViewById(R.id.btnPower_up);
        Button btnPurchase = dialog.findViewById(R.id.btnPurchase);
        Button btnBag = dialog.findViewById(R.id.btnBag);
        Button btnActivity = dialog.findViewById(R.id.btnActivityList);
        //
        TextView txtUserId = dialog.findViewById(R.id.tvAccount);
        txtUserId.setText("ACCOUNT: " + userId + "-->" + characterId);
        //
        TextView txtCurrentVip = dialog.findViewById(R.id.current_vip);
        TextView txtCurrentLevel = dialog.findViewById(R.id.current_level);
        TextView txtCurrentPower = dialog.findViewById(R.id.current_power);
        String curVip = SharedPreferencesHelper.getInstance(context).getData("cur_vip");
        if (curVip.isEmpty()) curVip = "0";
        txtCurrentVip.setText(curVip);
        String curLevel = SharedPreferencesHelper.getInstance(context).getData("cur_level");
        if (curLevel.isEmpty()) curLevel = "1";
        txtCurrentLevel.setText(curLevel);
        String curPower = SharedPreferencesHelper.getInstance(context).getData("cur_power");
        if (curPower.isEmpty()) curPower = "100";
        txtCurrentPower.setText(curPower);
        //

        btnVipUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnLvUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnPowUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnPurchase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnBag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(scaleDown);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(scaleUp);
                }
                return false;

            }
        });
        btnVipUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curVip = Integer.parseInt(txtCurrentVip.getText().toString());
                curVip++;
                txtCurrentVip.setText(String.valueOf(curVip));
                SharedPreferencesHelper.getInstance(context).saveData("cur_vip", String.valueOf(curVip));

                // Call ITS Tracking vip level up method
                itsSdk.vipUp(userId, characterId, serverInfo, curVip);
                Toast.makeText(MainActivity.this, "Vip level up: " + curVip, Toast.LENGTH_SHORT).show();
            }
        });

        btnLvUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curLevel = Integer.parseInt(txtCurrentLevel.getText().toString());
                curLevel++;
                txtCurrentLevel.setText(String.valueOf(curLevel));
                SharedPreferencesHelper.getInstance(context).saveData("cur_level", String.valueOf(curLevel));

                // Call ITS Tracking level up method
                itsSdk.levelUp(userId, characterId, serverInfo, curLevel);
                Toast.makeText(MainActivity.this, "Level up: " + curLevel, Toast.LENGTH_SHORT).show();
            }
        });

        btnPowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curPower = Integer.parseInt(txtCurrentPower.getText().toString());
                curPower += 100;
                txtCurrentPower.setText(String.valueOf(curPower));
                SharedPreferencesHelper.getInstance(context).saveData("cur_power", String.valueOf(curPower));

                // Call ITS Tracking power up method (custom event)
                ItsTrackingProperty itsProperty = new ItsTrackingProperty();
                itsProperty.put("power", String.valueOf(curPower));
                itsSdk.trackCustomEvent("PowerUp", itsProperty);

                Toast.makeText(MainActivity.this, "Power up: " + curPower, Toast.LENGTH_SHORT).show();
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListProductDialog(MainActivity.this);
            }
        });
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListActivityDialog(MainActivity.this);
            }
        });

        btnBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListItem(MainActivity.this);
            }
        });

        // Set the width and height of the dialog
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        dialog.show();
    }

    private void showListProductDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.UserDialog);
        builder.setTitle("Chọn một mục");
        builder.setItems(productNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String selectedProductId = productIDs[which];
                String amountsOfProductId = amounts[which];

                // Call ITS Tracking purchase method
                itsSdk.purchase("OrderIdTest", userId, characterId, serverInfo, selectedProductId, "", 1, "", Float.parseFloat(amountsOfProductId), "VND", Float.parseFloat(amountsOfProductId));
                Toast.makeText(MainActivity.this, "Bạn đã thanh toán: " + selectedProductId, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void showListActivityDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.UserDialog);
        builder.setTitle("SELECT ACTIVITY : ");
        builder.setItems(this.activityNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String selectedActivityId = activityIds[which];
                String selectedActivityName = activityNames[which];

                // Call ITS Tracking activity result method
                itsSdk.trackActivityResult(userId, characterId, serverInfo, selectedActivityId, "Success!");
                Toast.makeText(MainActivity.this, "You are done : " + selectedActivityName, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void showListItem(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.UserDialog);
        builder.setTitle("Select Item : ");
        builder.setItems(this.itemNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String selectedItemId = itemIds[which];
                String selectedItemName = itemNames[which];

                // Call ITS Tracking use item method
                itsSdk.useItem(userId, characterId, serverInfo, selectedItemId, 1);
                Toast.makeText(MainActivity.this, "You have used: " + selectedItemName, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}