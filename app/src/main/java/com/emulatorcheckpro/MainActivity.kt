package com.emulatorcheckpro

import android.app.Activity
import android.os.Bundle
import android.os.Build
import android.content.Intent
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    private lateinit var report: TextView
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(20))
            setBackgroundColor(0xFFF3F4F6.toInt())
        }
        val title = TextView(this).apply {
            text = "Emulator Check Pro"
            textSize = 25f
            setTextColor(0xFF111827.toInt())
            gravity = Gravity.CENTER
        }
        root.addView(title, LinearLayout.LayoutParams(-1, dp(54)))
        val subtitle = TextView(this).apply {
            text = "LDPlayer 14 • Device diagnostics"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(0xFF4B5563.toInt())
        }
        root.addView(subtitle, LinearLayout.LayoutParams(-1, dp(32)))
        val scan = Button(this).apply { text = "Run diagnostics" }
        root.addView(scan, LinearLayout.LayoutParams(-1, dp(52)))
        report = TextView(this).apply {
            textSize = 14f
            setTextColor(0xFF1F2937.toInt())
            setPadding(dp(14), dp(14), dp(14), dp(14))
            setBackgroundColor(0xFFFFFFFF.toInt())
        }
        val scroll = ScrollView(this)
        scroll.addView(report)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f).apply { topMargin = dp(14) })
        val share = Button(this).apply { text = "Share report"; isEnabled = false }
        root.addView(share, LinearLayout.LayoutParams(-1, dp(48)).apply { topMargin = dp(10) })
        var lastReport = ""
        scan.setOnClickListener { lastReport = makeReport(); report.text = lastReport; share.isEnabled = true }
        share.setOnClickListener {
            val send = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, lastReport) }
            startActivity(Intent.createChooser(send, "Share diagnostic report"))
        }
        setContentView(root)
    }

    private fun makeReport(): String {
        val pm = packageManager
        val gms = try { pm.getPackageInfo("com.google.android.gms", 0); "Installed" } catch (_: Exception) { "Not found" }
        val store = try { pm.getPackageInfo("com.android.vending", 0); "Installed" } catch (_: Exception) { "Not found" }
        val abis = Build.SUPPORTED_ABIS.joinToString(", ")
        val totalMem = Runtime.getRuntime().maxMemory() / (1024 * 1024)
        return """EMULATOR CHECK PRO — DIAGNOSTIC REPORT

Android release: ${Build.VERSION.RELEASE}
Android SDK: ${Build.VERSION.SDK_INT}
Manufacturer: ${Build.MANUFACTURER}
Model: ${Build.MODEL}
Device: ${Build.DEVICE}
Product: ${Build.PRODUCT}
Brand: ${Build.BRAND}
Hardware: ${Build.HARDWARE}
Supported ABI: $abis
App runtime max heap: ${totalMem} MB
Google Play Services: $gms
Google Play Store: $store

Privacy & limitations:
• This app reports standard Android build information only.
• It does not read, generate, or change IMEI/phone identifiers.
• It does not hide or alter emulator status or bypass app security checks.
• App compatibility is decided by each app's developer; this report cannot guarantee compatibility.
""".trimIndent()
    }
}
