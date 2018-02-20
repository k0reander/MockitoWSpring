package com.packt.csv;

class WUPRecord {
    String gupGipNummer;
    String eigenNummer;
    String investeringsType;
    Integer prioriteit;

    public void setGupGipNummer(String gupGipNummer) {
        this.gupGipNummer = gupGipNummer;
    }

    public void setEigenNummer(String eigenNummer) {
        this.eigenNummer = eigenNummer;
    }

    public void setInvesteringsType(String investeringsType) {
        this.investeringsType = investeringsType;
    }

    public void setPrioriteit(Integer prioriteit) {
        this.prioriteit = prioriteit;
    }

    @Override
    public String toString() {
        return "WUPRecord{" +
                "gupGipNummer='" + gupGipNummer + '\'' +
                ", eigenNummer='" + eigenNummer + '\'' +
                ", investeringsType='" + investeringsType + '\'' +
                ", prioriteit=" + prioriteit +
                '}';
    }
}
