Checkout
===================

Description
-----------

This is a theoretical implementation of a checkout system which can apply discounts
according to rules defined by the user,
which will be applied based on the items being checked out.
  
Notes
-------

* Both the Checkout and the PricingRule expect Item objects
* Item objects are matched on all fields
* Discounts are applied as items are checked out, to allow for real time feedback to 
  be implemented later if needed
* Three categories of PricingRule are currently implemented
    * X for the Price of Y: Purchase X number of items and only pay for Y number of items.
    * Bulk Discount: Purchase a certain amount of an item and receive 
      a specified reduction in price (in dollars) for each item.
    * Item Bundle: For every base item purchased, the bundled item can be added for 
      no increase in the total amount (the value of the bundled item is discounted from the total).
    