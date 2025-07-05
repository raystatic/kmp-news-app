import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

   init() {
        CommonModuleKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}