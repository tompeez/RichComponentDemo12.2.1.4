/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.resource;

import java.util.ListResourceBundle;

public class FileExplorerBundle
  extends ListResourceBundle
{
  public Object[][] getContents()
  {
    return _contents;
  }

  static final Object[][] _contents =
  {
    {"global.branding_name", "File Explorer"},
    {"global.no_row", "No File Item"},
    {"global.oracle", "Oracle"},
    {"global.properties", "Properties"},
    {"global.none", "None"},
    {"global.save", "Save"},
    {"global.cancel", "Cancel"},
    {"global.close", "Close"},
    {"global.newfile", "Create New File"},
    {"global.newfileinst1", "Please enter required file name field and expand the " +
    "property area by clicking the plus sign icon below to modify default file properties."},
    {"global.newfileinst2", "Note that the file will be created in the location:"},
    {"global.skin", "Select Skin"},

    {"about.fileexplorer", "About File Explorer"},
    {"about.fileexplorerdesc", "<b>File Explorer is a demo application to show the " +
    "features and components for Oracle Rich Application Development Framework (ADF).</b>"},
    {"about.richadfdesc", "<b>Oracle ADF Faces Rich Client extends the Apache " +
    "Trinidad component framework to provide a rich set of AJAX-enabled JSF " +
    "components that radically simplifies rich internet application development.</b>"},
    {"about.copyright", "Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved."},

    {"deletepopup.popuptitle", "Delete confirmation dialog"},
    {"deletepopup.confirmation", "<b>Are you sure you want to delete this file?</b>"},

    {"menu.file", "&File"},
    {"menu.new", "&New"},
    {"menu.edit", "&Edit"},
    {"menu.view", "&View"},
    {"menu.help", "&Help"},

    {"menuitem.file", "&File..."},
    {"menuitem.folder", "&Folder"},
    {"menuitem.delete", "Delete"},
    {"menuitem.properties", "Properties"},
    {"menuitem.movetoparent", "Move to Parent"},
    {"menuitem.copy", "Copy"},
    {"menuitem.paste", "Paste"},
    {"menuitem.delete", "Delete"},
    {"menuitem.refresh", "Refresh"},
    {"menuitem.about", "About"},
    {"menuitem.feedback", "Give Feedback"},
    {"menuitem.location", "Current Location: "},

    {"toolbarbutton.navbacktooltip", "Navigate Back"},
    {"toolbarbutton.navfwdtooltip", "Navigate Forward"},
    {"toolbarbutton.uptooltip", "Up"},
    {"toolbarbutton.locale", "Select Locale"},

    {"navigator.folders", "Folders"},
    {"navigator.newfile", "New File"},
    {"navigator.newfolder", "New Folder"},
    {"navigator.myfiles", "My Files"},
    {"navigator.shownewfileproperties", "Show File Properties"},
    {"navigator.hidenewfileproperties", "Hide File Properties"},
    {"navigator.search", "Search"},
    {"navigator.filenamesearch", "All or part of the file name"},
    {"navigator.typeoffilesearch", "What type of File?"},
    {"navigator.lastmodifiedsearch", "When was it modified?"},
    {"navigator.sizeoptionsearch", "What size is it?"},
    {"navigator.nofilesfound", "There were no files found"},
    {"navigator.filesfound", "Number of files found: "},

    {"sizeoptionsearch.any", "Don't know"},
    {"sizeoptionsearch.small", "Small (less than 100KB)"},
    {"sizeoptionsearch.medium", "Medium (less than 1MB)"},
    {"sizeoptionsearch.large", "Large (more than 1MB)"},

    {"lastmodifiedsearch.any", "Don't remember"},
    {"lastmodifiedsearch.week", "Within the last week"},
    {"lastmodifiedsearch.month", "Past month"},
    {"lastmodifiedsearch.year", "Within last year"},
    {"lastmodifiedsearch.specify", "Specify dates"},
    {"lastmodifiedsearch.from", "From: "},
    {"lastmodifiedsearch.to", "To: "},

    {"navglobal.oracledotcom", "&Oracle Corporation Home Page"},

    {"contents.name", "Name"},
    {"contents.type", "Type"},
    {"contents.size", "Size (KB)"},
    {"contents.lastmodified", "Date Modified"},
    {"contents.showproperties", "Show Properties"},

    {"contentView.table", "Table"},
    {"contentView.treetable", "Tree Table"},
    {"contentView.list", "List"},

    {"filetype.doc", "Document File"},
    {"filetype.folder", "File Folder"},
    {"filetype.html", "HTML File"},
    {"filetype.img", "Image File"},
    {"filetype.jscript", "JScript Script File"},
    {"filetype.misc", "File"},
    {"filetype.pdf", "PDF File"},
    {"filetype.txt", "Text File"},
    {"filetype.xls", "XLS File"},
    {"filetype.xml", "XML File"},
    {"filetype.zip", "Zip File"},

    {"fileproperties.name", "Name"},
    {"fileproperties.type", "Type"},
    {"fileproperties.description", "Description"},
    {"fileproperties.keywords", "Keywords"},
    {"fileproperties.readonly", "Read Only"},
    {"fileproperties.shared", "Shared"},
    {"fileproperties.hidden", "Hidden"},
    {"fileproperties.isfolder", "Is Folder"},
    {"fileproperties.size", "Size (KB)"},
    {"fileproperties.currentpath", "File Path"},
    {"fileproperties.lastmodified", "Last Modified"},
    {"fileproperties.created", "Created"},
    {"fileproperties.contains", "Contains"},

    {"propertiesdialog.general", "General"},
    {"propertiesdialog.history", "History"},
    {"propertiesdialog.attributes", "Attributes"},
    {"propertiesdialog.sharing", "Sharing"},
    {"propertiesdialog.others", "Others"},

    {"skin.simple", "simple"},
    {"skin.richdemo", "richDemo"},
    {"skin.fusion", "fusion"},
    {"skin.lowvision", "Low Vision"},

    {"error.nameisrequired", "Name is required"},
    {"error.invalidpathsummary", "Invalid Path."},
    {"error.invalidpathdetail", "Please choose the right file from the folders navigator."},

    {"help.help", "Help"},
    {"help.emailCustomerService", "Email Customer Service"},
    {"help.emailFileExplorer", "Email to FileExplorer.com Customer Service"},
    {"help.to", "To:"},
    {"help.attachment", "Attachment:"},
    {"help.name", "Name:"},
    {"help.emailAddress", "Email Address:"},
    {"help.issue", "Issue:"},
    {"help.cantAccessSite", "Can't access the site"},
    {"help.cantFindFile", "Can't find my file"},
    {"help.seeFileHistory", "Need to see file history"},
    {"help.otherQs", "Other Questions & Comments"},
    {"help.sendEmail", "Send Email"},
    {"help.cancel", "Cancel"},
    {"help.reset", "Reset"},
    {"help.submit", "Submit"},
    {"help.speakCustService", "Speak with Customer Service"},
    {"help.speakFileExplorerCustService", "Speak with a FileExplorer.com " +
    "Customer Service Representative"},
    {"help.available", "<b>We're available 24 hours a day, 7 days a week, " +
    "365 days a year.<br> Let us know a good time to call you, and we'll have " +
    "a customer service representative contact you.</b>"},
    {"help.pickDate", "Pick a date and time for us to call you"},
    {"help.phone", "Phone number where we should call you"},
    {"help.extension", " Extension"},
    {"help.altPhone", "Alternate phone number"},
    {"help.rateSite", "Rate the Site"},
    {"help.functionality", "Functionality"},
    {"help.easeOfUse", "Ease of Use"},
    {"help.performance", "Performance"},
    {"help.visualAppeal", "Visual Appeal"},
    {"help.newColorScheme", "If you were to choose a new color scheme for " +
    "FileExplorer, which colors would you prefer?"},
    {"help.primaryColor", "Primary Color"},
    {"help.secondaryColor", "Secondary Color"},
    {"help.aboutYourself", "Please tell us about yourself"},
    {"help.developer", "Developer"},
    {"help.designer", "Designer"},
    {"help.customer", "Customer"},
    {"help.manager", "Manager"},
    {"help.userPolls", "User Polls"},
    {"help.vote", "<big>Vote for your Favorite 5 File Types</big>"},
    {"help.availableFileTypes", "Available File Types"},
    {"help.top5", "Top 5 File Types (in order)"},
    {"help.thankYou", "Thank You"},
    {"help.backFileExplorer", "Back to File Explorer.com"},
    {"help.thankYouForContactingUs", "<big><b>Thank You</b> for submitting your " +
    "feedback.<br>We appreciate you took the time to contact us.</big>"},
  };
}
