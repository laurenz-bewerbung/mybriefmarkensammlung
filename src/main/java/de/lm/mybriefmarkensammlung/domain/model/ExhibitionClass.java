package de.lm.mybriefmarkensammlung.domain.model;

public enum ExhibitionClass {
    TRADITIONAL("Traditionelle Philatelie"),
    POSTAL_HISTORY("Postgeschichte"),
    THEMATIC("Thematische Philatelie"),
    AEROPHILATELY("Luftpost"),
    REVENUE("Fiskal-Philatelie"),
    YOUTH("Jugend-Philatelie"),
    OPEN("Offene Klasse");

    private final String displayName;

    ExhibitionClass(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}