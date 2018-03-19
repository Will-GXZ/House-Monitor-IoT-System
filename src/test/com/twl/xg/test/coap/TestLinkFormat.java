package com.twl.xg.test.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.server.resources.ResourceAttributes;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class TestLinkFormat {
  @Test
  public void test1() {
    // asynchronous

    System.out.println("ASYNCHRONOUS");

    CoapClient client = new CoapClient("coap://[fd00::c30c:0:0:2]:5683/.well-known/core");

    // synchronous
    CoapResponse response = client.get();
    String content1 = response.getResponseText();
    System.out.println("RESPONSE 1: " + content1 + "\n*****************************************");

    OptionSet optionSet = response.getOptions();
    System.out.println("\nOptions: \n" + optionSet.toString() + "\n***************************************\n");

    // resolve the information in application/link-format response body
    Set<WebLink> linkSet = client.discover();
    for (WebLink webLink : linkSet) {
      System.out.println(webLink.toString());
    }

    // test title
    for (WebLink webLink : linkSet) {
      ResourceAttributes ra = webLink.getAttributes();
      System.out.println(ra.getTitle());
    }
  }

  @Test
  public void testGetBatteryInfo() {
    System.out.println("************ Getting battery status *************");
    String sensorUrl = "coap://[fd00::c30c:0:0:2]:5683";
    String batteryUri = "/sensors/battery";
    CoapClient client = new CoapClient(sensorUrl + batteryUri);
    CoapResponse response = client.get();
    String content = response.getResponseText();
    System.out.println("Response Content:  " + content);
  }
}
