package org.example;

/**
 * @author mabbasi
 */
public class CustomAgent {

    public static final CustomAgent OTHER = new CustomAgent("", "", "", "");

    public final String platform , platform_version, app_name , app_version ;



    public CustomAgent(String platform , String platform_version, String app_name , String app_version) {
        this.app_name= app_name;
        this.app_version= app_version;
        this.platform=platform;
        this.platform_version= platform_version ;
    }

    public CustomAgent() {
        this.app_name= "";
        this.app_version= "";
        this.platform="";
        this.platform_version= "" ;
    }

    @Override
    public String toString() {
        return String.format("{\"platform\": %s, \"platform_version\": %s, \"app_name\": %s, \"app_version\": %s}",
                platform == null ? Constants.EMPTY_STRING : '"' + platform + '"',
                platform_version == null ? Constants.EMPTY_STRING : '"' + platform_version + '"',
                app_name == null ? Constants.EMPTY_STRING : '"' + app_name + '"',
                app_version == null ? Constants.EMPTY_STRING : '"' + app_version + '"');
    }



    @Override
    public boolean equals(Object other) {

        if (other == this) return true;
        if (!(other instanceof CustomAgent)) return false;

        CustomAgent o = (CustomAgent) other;
        return ((this.app_version != null && this.app_version.equals(o.app_version)) || this.app_version == o.app_version) &&
                ((this.app_name != null && this.app_name.equals(o.app_name)) || this.app_name == o.app_name) &&
                ((this.platform_version != null && this.platform_version.equals(o.platform_version)) || this.platform_version == o.platform_version) &&
                ((this.platform != null && this.platform.equals(o.platform)) || this.platform == o.platform)
                ;
    }

    @Override
    public int hashCode() {
        int h = app_version == null ? 0 : app_version.hashCode();
        h += app_name == null ? 0 : app_name.hashCode();
        h += platform_version == null ? 0 : platform_version.hashCode();
        h += platform == null ? 0 : platform.hashCode();
        return h;
    }

}
