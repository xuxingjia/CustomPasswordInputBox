# PasswordInputBox

### 样式展示:
![image](https://github.com/xuxingjia/PasswordInputBox/blob/master/images/wechat_20190611114704.gif)

### 加载
Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.xuxingjia:PasswordInputBox:-SNAPSHOT'
	}
  
## 使用手册

    <com.custompasswordinputbox.passwordinputbox.PasswordInputBox
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:amount="6" // box的个数
        app:textSize="15sp" // 设置字体大小
        app:inputBg="@mipmap/ic_edit_text_bg_black"  // 设置输入时的背景图片
        app:defaultBg="@mipmap/ic_set_new_password_grean"  //设置默认的背景图片
        app:boxHeight="50dp" //设置box的高度
        app:boxWidth="50dp"  //设置box的宽度
        app:margin="10dp" //设置box之间间距大小
        app:inputType="number"  /设置输入类型   phone text number password
        tools:ignore="MissingConstraints" />
	
	
## 初始化与事件监听

        PasswordInputBox viewById = findViewById(R.id.pib);
        viewById.setCommitListener(new PasswordInputBox.CommitListener() {//监听输入的信息
            @Override
            public void commitListener(String content) { //content 为输入的信息
	    	
            }
        });
