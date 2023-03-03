-keepattributes *Annotation*
-keepclassmembers class ** {
    public void on*Event(...);
}
-keep public class com.tencent.location.**{
    public protected *;
}
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class c.t.**{*;}
-keep class com.tencent.map.geolocation.**{*;}
-dontwarn  org.eclipse.jdt.annotation.**
-dontwarn  c.t.**
-dontwarn  android.location.Location
-dontwarn  android.net.wifi.WifiManager
-dontnote ct.**
