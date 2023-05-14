package com.code.visaoproto.barcode

import androidx.core.content.ContextCompat
import com.code.visaoproto.BaseLensActivity

class BarcodeActivity : BaseLensActivity() {

    override val imageAnalyzer = BarcodeAnalyzer()
    override fun startScanner() {
        scanBarcode()
    }

    private fun scanBarcode() {

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )

    }

}