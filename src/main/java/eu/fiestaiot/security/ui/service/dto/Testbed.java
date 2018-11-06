package eu.fiestaiot.security.ui.service.dto;

/**
 * Created by hungnguyendang on 18/07/2017.
 */
public class Testbed {
    private String iri;
    private String resourceID;
    private String name;
    private Integer testbedId;

    public Testbed() {
    }

    public Testbed(String iri, String resourceID, String name, Integer testbedId) {
        this.iri = iri;
        this.resourceID = resourceID;
        this.name = name;
        this.testbedId = testbedId;
    }

    public String getIri() {
        return iri;
    }

    public Testbed setIri(String iri) {
        this.iri = iri;
        return this;
    }

    public String getResourceID() {
        return resourceID;
    }

    public Testbed setResourceID(String resourceID) {
        this.resourceID = resourceID;
        return this;
    }

    public String getName() {
        return name;
    }

    public Testbed setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getTestbedId() {
        return testbedId;
    }

    public Testbed setTestbedId(Integer testbedId) {
        this.testbedId = testbedId;
        return this;
    }

    @Override
    public String toString() {
        return "Testbed{" +
            "iri='" + iri + '\'' +
            ", resourceID='" + resourceID + '\'' +
            ", name='" + name + '\'' +
            ", testbedId=" + testbedId +
            '}';
    }
}
