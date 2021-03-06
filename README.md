# How to run
* Download zip from the github url. 
* Unzip in directory of your choice. 
* From directotry GildedRoseTrial-master\GildedRoseInventoryApi call mvnw spring-boot:run. 
* Use urls http://localhost:8080/gildedRose/inventoryList for the inventory list - each call counts towards suging. 
* You can buy with http://localhost:8080/gildedRose/purchase?itemName=chair. You can get other item names from the inventory list.
* users are user1 user1Pass and user2 user2Pass

# Alternative Run 
Clone from github using STS. Open using project wizard chose General > Projects from Folder or archive. I have only ever run the tests from STS. You runAs main for the GildedRoseInventoryApiApplication class

# Known Flaws
* Should change Rest API to use post for purchase. Left as get for easier testing
* Move messages to a message source
* Surge only goes up never down (out of scope: in real life would ask) 
* Use better authentication than basic.
* No acccount tracking of money spent (out of scope: in real life would ask)
* User cannot be sure of the price when they buy (out of scope? -in real life would ask) One solution use sends the expected price with the buy request add a new syncronized method on the SurgePricer provides the current suge price.  Compare and that to what the user submitted and reject if they don't match
* hard coded uses (goes with no database out of scope)

# Why I built it this way
* You want to make it runnable and check it out of repository so using Spring boot with embeded Tomcat seemed good.
* Rest API with Spring straight foward soluton for http API.
* Likewise JSON for the returned data. Is simple works with Javascript. Could have used XML but JSON issimple gets the job don.
* I can be sure that the user is authenticated when they hit buy because of the security - also tested in Junit tests (I am calling Juni test with mockMvc integration testing)
* Basic is not the best authentication but it works for this demo and some time pressure due to my vacation - better to get something wokring
* I built the app with three layers web(controller), business (service, manager) and repository dao. That is what I would do with a real database. If you want to put in a database your changes could be as limited a changing the InventoryDaoImpl and take synchronized off the buy. The repository would handle hyou want to lock in place of the synchronize
* Not sure how to answer "What kind of architecture?" question. Not sure what name I would use for a type: "Spring Rest with 3 tier"


# Sample responses
* You can see the requests above
* Inventory List response [{"name":"Action1","description":"Super Man Fair Condition","price":100000,"available":1},{"name":"chair","description":"Antique Chair","price":1000,"available":1},{"name":"coin","description":"Rare Roman Denarius","price":5000,"available":10}]
* Buy(Purchase) {"result":"Purchased.","itemsRequested":1,"itemsPurchased":1}

# Edited Copy of Javadoc for SurgePricer 

This class implements the surge pricing. If there are a certain number of views within a given time window the price of all items goes up by a certain percentage. The number of views, the duration of the time window and the percent amount the price of items increases are all set in the constructor. Calling  calculateSurgePriceMultiplierPercent determines if the price should surge or not. It records the view time using java.util.Date as the source of a long that records the surge time. It also increments the surge price multiplier. This is an int that contains the percentage impact of the surge pricing. Initially it is 100 for 100% which would mean no surge pricing. The method is to be called every time the list of items is retrieved. If the surge increase is 10% than after the first surge the multiplier would be 110. Every price is 10% higher. Call applySurgeMultiplier for applying this to a price. It encapsulates rounding and the percentage.<p>
Usage: Call exactly calculateSurgePriceMultiplierPercent once before each action that counts towards surging. At the time of writing the only known such action was calling views of the item list. Store the surge multiplier. Afterwards apply the multiplier to each price. Use the provided routine for calculating. Note there is no compounding. Two 10% surges lead to a multiplier of 120 not 121. Also once a surge occurs the class resets itself and a complete count of views must happen before the next surge. In other words after 10 views in the window you get a surge you need 10 more views in the window to get the next surge. The method is synchronized so if two views happen concurrently internal state is protected. See InventoryDaoImpl<p>
Implementation: Use an array of longs in a round robin style to record view times. Each new view replaces the oldest previous view. The array is always initialized to longs corresponding to a very old time so even when first initialized (or reset after a surge) the array acts as if populated with long ago views. 

# Additional Comments on SurgePricer

You can overide getTimeOfViewInMilliseconds() in a subclass for testing. This is what I did  with SurgePricerDeterministic. I can have the pricer respond to a passed in vararg of longs and that way I can explicitly test the pricer on scenarios and can verify that the surge happens when I want it to. The longs are easier to work with than java.util.Date and I can do everything I need with them. All that matters is the sequence of the longs and the delta corresponding to times. I run through a few scenarios: 
* I paid attention to making sure that my round-robin array wrapped.
* Equal items in case things happen concurrently (rare I know)
* Surge on the nth view tested.
* Surge on after the nth view tested.
* Made sure reset of surge happened.

# Other testing
The other testing I did was integration tests on the controller. Verify surge and verify buy and the three options: success, no such item and out of stock. Note that the actual buy of an item is synchronized. Note this tests the InventoryDaoImpl as a matter of course

