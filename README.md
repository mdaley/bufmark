# README

It's a pity that there isn't a POJO <-> protobuf converter library.
Should we transform protobuf objects when they come in so the code can be simpler (this would be slower though
Protobuf can't deal with this that are not set - a default value has to be used to coding around, e.g. by having
a specific NOT_SET value for enums; if something is not set it still has a default value. Specific use of a not
set flag can help, but things just get more complicated.

