import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PrintPlugin)
public class PrintPlugin: CAPPlugin {

    @objc func print(_ call: CAPPluginCall) {
        guard let webView = self.bridge?.webView else {
            call.reject("WebView is not available")
            return
        }

        DispatchQueue.main.async {
            let webviewPrint = webView.viewPrintFormatter()
            let printInfo = UIPrintInfo(dictionary: nil)

            // Custom job name from the JavaScript call, with a default value
            printInfo.jobName = call.getString("jobName") ?? "Document"
            printInfo.outputType = .general

            let printController = UIPrintInteractionController.shared
            printController.printInfo = printInfo
            printController.printFormatter = webviewPrint

            // Basic error handling in the completion handler
            printController.present(animated: true, completionHandler: { (_, _, error) in
                if let error = error {
                    call.reject("Printing failed: \(error.localizedDescription)")
                } else {
                    call.resolve()
                }
            })
        }
    }
}

