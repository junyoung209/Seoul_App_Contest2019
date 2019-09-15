package com.example.seoul.Group;

public class GroupData {
    private String crewName;
    private String crewRegion;
    private String crewHost;
    private String crewInfo;

    public GroupData(String crewName,String crewRegion,String crewHost,String crewInfo)
    {
        this.crewName=crewName;
        this.crewRegion=crewRegion;
        this.crewHost=crewHost;
        this.crewInfo=crewInfo;
    }


    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getCrewRegion() {
        return crewRegion;
    }

    public void setCrewRegion(String crewRegion) {
        this.crewRegion = crewRegion;
    }

    public String getCrewHost() {
        return crewHost;
    }

    public void setCrewHost(String crewHost) {
        this.crewHost = crewHost;
    }

    public String getCrewInfo() {
        return crewInfo;
    }

    public void setCrewInfo(String crewInfo) {
        this.crewInfo = crewInfo;
    }
}
