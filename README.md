### **Custom OTP Library**
This library provides a convenient way to integrate custom OTP (One-Time Password) input fields into your Android applications.

### **Installation**
To use this library in your Android project, add the following dependency to your project:

Add it in your root build.gradle at the end of repositories:
```
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

Add this dependency in your build.gradle(Module):
```
implementation 'com.github.hamzabinbashir23:custom-otp:1.0'
```

### **Attributes**
The library offers the following attributes for customization:

- app:fieldLength: Sets the length of each OTP field.
- app:dashPosition: Sets the position where the dash should appear between OTP fields.
- app:textSize: Sets the text size of the OTP fields.
- app:fieldRadius: Sets the corner radius of the OTP fields.
- app:textColor: Sets the text color of the OTP fields.
- app:codeLength: Sets the total length of the OTP.
- app:textBackgroundColor: Sets the background color of the OTP fields.
- app:fieldStrokeColor: Sets the stroke color of the OTP fields.
- app:fieldStrokeWidth: Sets the stroke width of the OTP fields.

### **Usage**

**Adding in xml**
```
<com.example.customotpview.NymOtpView
     android:id="@+id/otp"
     android:layout_width="match_parent"
     app:dashPosition="2"
     android:layout_height="wrap_content" />
```

**Setting Number of OTP Fields**
To specify the number of OTP fields, use the setOtpFields() method:

```
binding.setOtpFields(4)
```
This will create 6 OTP input fields.

**Setting Dash Between Fields**
To insert a dash between specific OTP fields, utilize the setDashAtPosition() method:

```
binding.setDashAtPosition(2)
```
This will add a dash between the third and fourth OTP input fields.

**Listening to Changes**
To listen to changes in the OTP input fields, implement the onChange interface:

```
binding.onChange {
//your work here
}
```
**Customizing Active Field Text Color**
To customize the text color of the active OTP field, call the setActiveFieldBackground() method inside the onChange listener:

```
binding.onChange {
//your work here
        binding.setActiveFieldBackground();

}
```

This will change the text color of the active OTP field according to your customizations.

# **Result:**

![image_2024-01-29_172823058](https://github.com/hamzabinbashir23/CustomOtp/assets/60214407/244b0e92-0e55-4137-8c52-8c216e877e14)
![image_2024-01-29_172932284](https://github.com/hamzabinbashir23/CustomOtp/assets/60214407/102e0156-3f8b-402f-97c4-0198030a8fe2)
