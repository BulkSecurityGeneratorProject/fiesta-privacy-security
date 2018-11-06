package eu.fiestaiot.security.ui.web.rest.vm;

import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.domain.FiestaUser;

import java.util.ArrayList;
import java.util.List;

public class EndpointsConfigVM {
    public List<Endpoint> endpoints = new ArrayList<>();

    public List<EndpointUser> endpointUsers = new ArrayList<>();

    public boolean isPrivate;
    public boolean isPublic;
}
