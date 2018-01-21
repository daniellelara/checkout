
CHECKOUT
=================================

Setup Stages:
=================================

1. Clone Repo - `git clone https://github.com/daniellelara/checkout.git`
2. Run `sbt compile` when open at the root folder
3. `sbt run` into terminal when open at the root folder

you will be prompted to add SKU rules one by one, followed by input each item into the command line to checkout.
When you type in 'end', the final cost will appear in the command line.

Tests have been written for the checkoutService which can be found in src/test/scala/CheckoutServiceSpec