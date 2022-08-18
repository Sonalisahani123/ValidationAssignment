package com.test.vagrant;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class RCBTeamTest {

	JsonPath jsonpath = new JsonPath(RCBTeamPayload.teamRCB());
	int players = jsonpath.getInt("player.size()");

	@Test
	public void validate_foreignPlayer() {
		String foreignPlayers = null;
		for (int i = 0; i < players; i++) {
			String country = jsonpath.getString("player[" + i + "].country");
			if (!country.equalsIgnoreCase("India")) {
				foreignPlayers = jsonpath.getString("player[" + i + "].country");
				Assert.assertFalse(foreignPlayers.contains("India"));
			}

		}

	}

	@Test
	public void validate_wicketKeeper() {

		String role = null;
		for (int i = 0; i < players; i++) {
			role = jsonpath.getString("player[" + i + "].role");
			if (role.equals("Wicket-keeper")) {
				break;
			}
		}
		System.out.println(role);
		Assert.assertEquals(role, "Wicket-keeper");
	}

}
