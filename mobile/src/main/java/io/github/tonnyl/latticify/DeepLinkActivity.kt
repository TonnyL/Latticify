package io.github.tonnyl.latticify

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler

/**
 * Created by lizhaotailang on 28/12/2017.
 *
 * The activity with the scheme you'd like to handle in your `AndroidManifest.xml` file
 * Annotate it with [DeepLinkHandler] and provide a list of [com.airbnb.deeplinkdispatch.DeepLinkModule]
 * annotated class(es).
 */
@DeepLinkHandler(LatticifyDeepLinkModule::class)
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DeepLinkDelegate, MangoDeepLinkModuleLoader is generated at compile-time.
        val deepLinkDelegate = DeepLinkDelegate(LatticifyDeepLinkModuleLoader())

        // Delegate the deep link handling to DeepLinkDispatch.
        // It will start the correct Activity based on the incoming Intent URI
        deepLinkDelegate.dispatchFrom(this)

        // Finish this Activity since the correct one has been just started
        finish()

    }

}