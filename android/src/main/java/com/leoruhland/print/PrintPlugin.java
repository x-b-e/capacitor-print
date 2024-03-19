package com.leoruhland.print;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.webkit.WebView;
import com.getcapacitor.CapacitorWebView;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Print")
public class PrintPlugin extends Plugin {

    private Print implementation = new Print();

  @PluginMethod
  public void print(PluginCall call) {
    String jobName = call.getString("jobName", "Document");

    // Check if PrintManager is available
    PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
    if (printManager == null) {
      call.reject("Print Service is not available");
      return;
    }

    getActivity().runOnUiThread(() -> {
      try {
        WebView webView = getBridge().getWebView();

        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());

        call.resolve();
      } catch (Exception e) {
        call.reject("Failed to print", e);
      }
    });
  }
}
