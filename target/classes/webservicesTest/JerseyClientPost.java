package com.wallmart.web.webservices;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientPost {

	 public static void main(String[] args) {

			try {
				Client client = Client.create();
				String input = "My_task_from_web_service";
				WebResource webResource = client
				   .resource("http://localhost:8080/WallmartAssignment/rest/task/create/962/"+input);

				

				ClientResponse response = webResource.type("application/json")
				   .post(ClientResponse.class, "");

				System.out.println(response);
				if (response.getStatus() != 201) {
					throw new RuntimeException("Failed : HTTP error code : "
					     + response.getStatus());
				}

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

			  } catch (Exception e) {

				e.printStackTrace();

			  }

			}

}
