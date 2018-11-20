package wintec_cfit_managers.wintecdpm;

public class Module {
    String ModuleCode;
    String Name;
    int Level;
    int Credits;
    String Prereq;
    String Coreq;
    int Year;
    String Pathway;
    String Desc;

    Module(String n, int l, int cd, String pr, String cr, int y, String p, String d){
        this.Name = n;
        this.Level = l;
        this.Credits = cd;
        this.Prereq = pr;
        this.Coreq = cr;
        this.Year = y;
        this.Pathway = p;
        this.Desc = d;
    }
    public Module(String id, String n, int l, int cd, String pr, String cr, int y, String p, String d) {
        this.ModuleCode = id;
        this.Name = n;
        this.Level = l;
        this.Credits = cd;
        this.Prereq = pr;
        this.Coreq = cr;
        this.Year = y;
        this.Pathway = p;
        this.Desc = d;
    }

    public String getModuleCode() {
        return ModuleCode;
    }
    public String getModuleName() {
        return Name;
    }
    public int getModuleLevel() { return Level; }
    public int getModuleCredits() {
        return Credits;
    }
    public String getModulePrereq() {
        return Prereq;
    }
    public String getModuleCoreq() {
        return Coreq;
    }
    public int getModuleYear() {
        return Year;
    }
    public String getModulePathway() {
        return Pathway;
    }
    public String getModuleDesc() {
        return Desc;
    }
}
