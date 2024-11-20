# RxActivityResult
an Android RX ActivityResult Component

make Android ActivityResult callback more easily


### Setup:

minSdkVersion: 15

``` 
allprojects {
    repositories {
        ...
        jcenter()
	maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.xattacker:RxActivityResult:1.0.2'
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
