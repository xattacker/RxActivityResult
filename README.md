# RxActivityResult
a Android RX ActivityResult Component
make Android ActivityResult callback more easily


### Setup:

minSdkVersion: 15

``` 
allprojects {
    repositories {
        ...
        jcenter()
    }
}

dependencies {
    implementation 'com.xattacker.android:RxActivityResult:1.0.1'
}
``` 

### How to use:
``` 
	val intent = Intent(this, ResultActivity::class.java)
	
        val rx = RxActivityResult(this)
        rx.startActivityForResult(intent)
            .subscribe {
	    	result ->
                android.util.Log.d("aaa", "result: " + result.resultCode)

                if (result.resultCode == Activity.RESULT_OK && result.data != null)
                {
                    android.util.Log.d("aaa", "result intent: " + result.data!!.extras.getString("result"))
                }
            }
``` 
