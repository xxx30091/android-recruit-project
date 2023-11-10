package `in`.hahow.android_recruit_project.widget

import android.content.Context
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResponseListener
import kotlin.properties.Delegates


//class GoogleBillingManager private constructor(private val context: Context) {
//
//    private val TAG = "GoogleBillingManager"
//    lateinit var billingClient: BillingClient
//    private var isServiceConnected = false
//
//    init {
//        initializeBillingClient()
//    }
//
//    fun initializeBillingClient() {
//        billingClient = BillingClient.newBuilder(context)
//            .setListener(purchasesUpdatedListener)
//            .enablePendingPurchases() // Not used for subscriptions.
//            .build()
//
//        billingClient.startConnection(object : BillingClientStateListener {
//            override fun onBillingSetupFinished(billingResult: BillingResult) {
//                if (billingResult.responseCode == BillingResponseCode.OK) {
//                    isServiceConnected = true
//                    // Billing client is ready
//                }
//            }
//
//            override fun onBillingServiceDisconnected() {
//                isServiceConnected = false
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//            }
//        })
//    }
//
//    // Implement your PurchasesUpdatedListener
//    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
//        // Process the result
//        if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
//            for (purchase in purchases) {
//                // Handle the purchase
//                val orderId = purchase.orderId
//                Log.i("Arthur", "$TAG, Response is OK")
//                Log.i("Arthur", "purchase: $purchase")
//            }
//        } else {
//            Log.i("Arthur", "$TAG, Response NOT OK")
//        }
//    }
//
//
//    companion object {
//        private var instance: GoogleBillingManager by Delegates.notNull()
//        fun getInstance(): GoogleBillingManager {
//            return instance
//        }
//    }
//}

class BillingClientWrapper(context: Context) : PurchasesUpdatedListener {

    interface OnQueryProductsListener {
        fun onSuccess(products: List<SkuDetails>)
        fun onFailure(error: Error)
    }

    class Error(val responseCode: Int, val debugMessage: String)

    private val billingClient = BillingClient
        .newBuilder(context)
        .enablePendingPurchases()
        .setListener(this)
        .build()

    fun queryProducts(listener: OnQueryProductsListener) {
        val skusList = listOf("premium_sub_month", "premium_sub_year", "some_inapp")
        queryProductsForType(
            skusList,
            BillingClient.SkuType.SUBS
        ) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val products = skuDetailsList ?: mutableListOf()
                queryProductsForType(
                    skusList,
                    BillingClient.SkuType.INAPP
                ) { billingResult, skuDetailsList ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        products.addAll(skuDetailsList ?: listOf())
                        listener.onSuccess(products)
                    } else {
                        listener.onFailure(
                            Error(billingResult.responseCode, billingResult.debugMessage)
                        )
                    }
                }
            } else {
                listener.onFailure(
                    Error(billingResult.responseCode, billingResult.debugMessage)
                )
            }
        }
    }

    private fun queryProductsForType(
        skusList: List<String>,
        @BillingClient.SkuType type: String,
        listener: SkuDetailsResponseListener
    ) {
        onConnected {
            billingClient.querySkuDetailsAsync(
                SkuDetailsParams.newBuilder().setSkusList(skusList).setType(type).build(),
                listener
            )
        }
    }

    private fun onConnected(block: () -> Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                block()
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchaseList: MutableList<Purchase>?) {
        // here come callbacks about new purchases
    }
}