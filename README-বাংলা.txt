নোয়াপাড়া বাজার – Payment SMS Maker (Android)

এই প্রজেক্টে আপনার দেওয়া index.html ফাইলটি Android অ্যাপের ভেতরে রাখা হয়েছে।
অ্যাপটি অফলাইনে চলে; SMS Preview, Copy এবং Share সুবিধা Android-এর সাথে যুক্ত।

APK বানানোর সহজ পদ্ধতি:
১. ZIP ফাইলটি GitHub-এ নতুন repository হিসেবে আপলোড করুন।
২. GitHub repository-তে Actions ট্যাব খুলুন।
৩. "Build Android APK" workflow চালু করুন (প্রথম push-এও নিজে থেকে চলবে)।
৪. কাজ শেষ হলে workflow run খুলে Artifacts থেকে Noapara-Payment-SMS-APK ডাউনলোড করুন।
৫. ZIP-এর ভেতরের app-debug.apk ফোন ও ট্যাবলেটে ইনস্টল করুন।
   Android-এ ইনস্টল করার সময় প্রয়োজন হলে এই ফাইলের জন্য "Install unknown apps" অনুমতি দিন।

Android Studio থাকলে:
- এই ফোল্ডারটি Open করুন।
- Gradle sync শেষ হলে Build > Build Bundle(s) / APK(s) > Build APK(s) নির্বাচন করুন।
- APK পাওয়া যাবে app/build/outputs/apk/debug/app-debug.apk পথে।

নোট:
- এটি debug APK; ব্যক্তিগত ব্যবহার/পরীক্ষার জন্য।
- HTML-এ থাকা মূল ফর্ম ও ডিজাইন অপরিবর্তিত রাখা হয়েছে।
- APK বানানোর আগে/পরে আসল দোকানদারের তথ্য দিয়ে পরীক্ষা করুন।
