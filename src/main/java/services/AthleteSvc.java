package services;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import connector.JStravaV3;
import entities.athlete.Athlete;
import model.Member;
import com.google.gson.Gson;
import dao.MemberDAO;

@Path("/athlete")
public class AthleteSvc {
	
	
} 