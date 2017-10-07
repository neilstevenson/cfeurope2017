# cfeurope2017

https://www.cloudfoundry.org/event/europe-2017/
 
## Module `legacy`
The `legacy` module holds the legacy data model stored in the RDMBS, and it is used by all
the microservices.

It's not good practice for microservices to have shared modules, as this couples them together.

Here it is done to avoid have too many modules in the example.