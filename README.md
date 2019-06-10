# transferService
Simple money transfer service.  Uses Jax-RS with an embedded jetty server.  Will use an in memory database.

A few assumptions:
1.  Currency conversion is done through a local database.
2.  The currency of a transfer must match that of target account.
3.  No negative transfers allowed.
4.  I allow a 0 transfer, maybe we want this to verify accounts exist.
