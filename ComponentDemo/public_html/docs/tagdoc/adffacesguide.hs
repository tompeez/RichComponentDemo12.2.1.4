<?xml version='1.0' ?>

<helpset  version="1.1" >

 <title>ADF Faces Tag Documentation</title>

 <maps>
   <mapref location="tagdoc/map.xml" engine="oracle.help.engine.XMLMapConventionEngine"/>
   <mapref location="tocmap.xml"/>
 </maps>

 <links>
 </links>

 <view>
   <name>ADF_FACES_TAG_DOCUMENTATION</name>
   <label>Search</label>
   <title>ADF Faces Tag Documentation - 12.1.3.0.0</title>
   <type>oracle.help.navigator.searchNavigator.SearchNavigator</type>
   <data engine="oracle.help.engine.SearchEngine">tagdoc/adffacestags.idx</data>
 </view>

 <view>
   <name>ADF_FACES_JAVADOC</name>
   <label>Search</label>
   <title>ADF Faces JavaDoc API</title>
   <type>oracle.help.navigator.searchNavigator.SearchNavigator</type>
   <data engine="oracle.help.engine.SearchEngine">multiproject/adffacesapi.idx</data>
 </view>
</helpset>
