@0xe6dd17e2bf132f2f;

using Java = import "/capnp/java.capnp";
$Java.package("com.sequsoft.bufmark.capnp");

enum Sex {
  male @0;
  female @1;
  neutral @2;
}