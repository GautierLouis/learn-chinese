import SwiftUI


//@MainActor
//class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {
//    func application(
//        _ application: UIApplication,
//        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
//    ) -> Bool {
//
//        // ============================================
//        // INITIALIZE FIREBASE
//        // ============================================
//        FirebaseApp.configure()
//        print("✅ Firebase initialized")
//
//        // ============================================
//        // SETUP PUSH NOTIFICATIONS
//        // ============================================
//
//        // Set notification center delegate
//        UNUserNotificationCenter.current().delegate = self
//
//        // Request notification permissions
//        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
//        UNUserNotificationCenter.current().requestAuthorization(
//            options: authOptions
//        ) { granted, error in
//            if let error = error {
//                print("❌ Notification permission error: \(error.localizedDescription)")
//            } else {
//                print(granted ? "✅ Notification permission granted" : "⚠️ Notification permission denied")
//            }
//        }
//
//        // Register for remote notifications
//        application.registerForRemoteNotifications()
//
//        // Set FCM messaging delegate
//        Messaging.messaging().delegate = self
//
//        return true
//    }
//
//    // ============================================
//    // APNS TOKEN REGISTRATION
//    // ============================================
//
//    func application(
//        _ application: UIApplication,
//        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
//    ) {
//        // Pass APNS token to Firebase
//        Messaging.messaging().apnsToken = deviceToken
//        print("✅ APNS Token registered with Firebase")
//    }
//
//    func application(
//        _ application: UIApplication,
//        didFailToRegisterForRemoteNotificationsWithError error: Error
//    ) {
//        print("❌ Failed to register for remote notifications: \(error.localizedDescription)")
//    }
//
//    // ============================================
//    // HANDLE NOTIFICATIONS WHEN APP IS IN FOREGROUND
//    // ============================================
//
//    nonisolated func userNotificationCenter(
//           _ center: UNUserNotificationCenter,
//           willPresent notification: UNNotification,
//           withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
//       ) {
//           let userInfo = notification.request.content.userInfo
//           
//           print("📱 Notification received in foreground:")
//           print(userInfo)
//           
//           // Show notification even when app is in foreground
//           completionHandler([[.banner, .sound, .badge]])
//       }
//
//    // ============================================
//    // HANDLE NOTIFICATION TAP
//    // ============================================
//
//    nonisolated func userNotificationCenter(
//            _ center: UNUserNotificationCenter,
//            didReceive response: UNNotificationResponse,
//            withCompletionHandler completionHandler: @escaping () -> Void
//        ) {
//            let userInfo = response.notification.request.content.userInfo
//            
//            print("👆 Notification tapped:")
//            print(userInfo)
//            
//            // Extract data from notification
//            if let title = userInfo["title"] as? String {
//                print("Title: \(title)")
//            }
//            
//            if let body = userInfo["body"] as? String {
//                print("Body: \(body)")
//            }
//            
//            // You can pass this data to your Kotlin code if needed
//            // Example: NotificationHandler.shared.handleNotification(userInfo)
//            
//            completionHandler()
//        }
//    
//    // ============================================
//    // FCM TOKEN HANDLING
//    // ============================================
//
//    nonisolated func messaging(
//            _ messaging: Messaging,
//            didReceiveRegistrationToken fcmToken: String?
//        ) {
//            guard let token = fcmToken else {
//                print("❌ FCM Token is nil")
//                return
//            }
//            
//            print("🔑 FCM Token: \(token)")
//            
//            // Store token for access from Kotlin or send to your server
//            UserDefaults.standard.set(token, forKey: "fcmToken")
//            
//            // Optional: Send token to your server
//            // sendTokenToServer(token)
//        }
//}


@main
struct iOSApp: App {

    // Register AppDelegate for Firebase setup
    //@UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        
        WindowGroup {
            ContentView()
        }
    }
}
