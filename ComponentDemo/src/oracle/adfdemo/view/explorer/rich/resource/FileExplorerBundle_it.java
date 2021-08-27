/* Copyright (c) 2008, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION

   PRIVATE CLASSES

   NOTES

 */
package oracle.adfdemo.view.explorer.rich.resource;

import java.util.ListResourceBundle;

public class FileExplorerBundle_it
  extends ListResourceBundle
{
  public Object[][] getContents()
  {
    return _contents;
  }

  static final Object[][] _contents =
  {
    {"global.branding_name", "File Explorer_it"},
    {"global.no_row", "No File Item_it"},
    {"global.oracle", "Oracle_it"},
    {"global.properties", "Properties_it"},
    {"global.none", "None_it"},
    {"global.save", "Save_it"},
    {"global.cancel", "Cancel_it"},
    {"global.close", "Close_it"},
    {"global.newfile", "Create New File_it"},
    {"global.newfileinst1", "Please enter required file name field and expand the " +
    "property area by clicking the plus sign icon below to modify default file properties._it"},
    {"global.newfileinst2", "Note that the file will be created in the location:_it"},
    {"global.skin", "Select Skin_it"},

    {"about.fileexplorer", "About File Explorer_it"},
    {"about.fileexplorerdesc", "<b>File Explorer is a demo application to show the " +
    "features and components for Oracle Rich Application Development Framework (ADF).</b>_it"},
    {"about.richadfdesc", "<b>Oracle ADF Faces Rich Client extends the Apache " +
    "Trinidad component framework to provide a rich set of AJAX-enabled JSF " +
    "components that radically simplifies rich internet application development.</b>_it"},
    {"about.copyright", "Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved."},

    {"deletepopup.popuptitle", "Delete confirmation dialog_it"},
    {"deletepopup.confirmation", "<b>Are you sure you want to delete this file?</b>_it"},

    {"menu.file", "&File_it"},
    {"menu.new", "&New_it"},
    {"menu.edit", "&Edit_it"},
    {"menu.view", "&View_it"},
    {"menu.help", "&Help_it"},

    {"menuitem.file", "&File..._it"},
    {"menuitem.folder", "&Folder_it"},
    {"menuitem.delete", "Delete_it"},
    {"menuitem.properties", "Properties_it"},
    {"menuitem.movetoparent", "Move to Parent_it"},
    {"menuitem.copy", "Copy_it"},
    {"menuitem.paste", "Paste_it"},
    {"menuitem.delete", "Delete_it"},
    {"menuitem.refresh", "Refresh_it"},
    {"menuitem.about", "About_it"},
    {"menuitem.feedback", "Give Feedback_it"},
    {"menuitem.location", "Current Location: _it"},

    {"toolbarbutton.navbacktooltip", "Navigate Back_it"},
    {"toolbarbutton.navfwdtooltip", "Navigate Forward_it"},
    {"toolbarbutton.uptooltip", "Up_it"},
    {"toolbarbutton.locale", "Select Locale_it"},

    {"navigator.folders", "Folders_it"},
    {"navigator.newfile", "New File_it"},
    {"navigator.newfolder", "New Folder_it"},
    {"navigator.myfiles", "My Files_it"},
    {"navigator.shownewfileproperties", "Show File Properties_it"},
    {"navigator.hidenewfileproperties", "Hide File Properties_it"},
    {"navigator.search", "Search_it"},
    {"navigator.filenamesearch", "All or part of the file name_it"},
    {"navigator.typeoffilesearch", "What type of File?_it"},
    {"navigator.lastmodifiedsearch", "When was it modified?_it"},
    {"navigator.sizeoptionsearch", "What size is it?_it"},
    {"navigator.nofilesfound", "There were no files found_it"},
    {"navigator.filesfound", "Number of files found: _it"},

    {"sizeoptionsearch.any", "Don't know_it"},
    {"sizeoptionsearch.small", "Small (less than 100KB)_it"},
    {"sizeoptionsearch.medium", "Medium (less than 1MB)_it"},
    {"sizeoptionsearch.large", "Large (more than 1MB)_it"},

    {"lastmodifiedsearch.any", "Don't remember_it"},
    {"lastmodifiedsearch.week", "Within the last week_it"},
    {"lastmodifiedsearch.month", "Past month_it"},
    {"lastmodifiedsearch.year", "Within last year_it"},
    {"lastmodifiedsearch.specify", "Specify dates_it"},
    {"lastmodifiedsearch.from", "From: _it"},
    {"lastmodifiedsearch.to", "To: _it"},

    {"navglobal.oracledotcom", "&Oracle Corporation Home Page_it"},

    {"contents.name", "Name_it"},
    {"contents.type", "Type_it"},
    {"contents.size", "Size (KB)_it"},
    {"contents.lastmodified", "Date Modified_it"},
    {"contents.showproperties", "Show Properties_it"},

    {"contentView.table", "Table_it"},
    {"contentView.treetable", "Tree Table_it"},
    {"contentView.list", "List_it"},

    {"filetype.doc", "Document File_it"},
    {"filetype.folder", "File Folder_it"},
    {"filetype.html", "HTML File_it"},
    {"filetype.img", "Image File_it"},
    {"filetype.jscript", "JScript Script File_it"},
    {"filetype.misc", "File_it"},
    {"filetype.pdf", "PDF File_it"},
    {"filetype.txt", "Text File_it"},
    {"filetype.xls", "XLS File_it"},
    {"filetype.xml", "XML File_it"},
    {"filetype.zip", "Zip File_it"},

    {"fileproperties.name", "Name_it"},
    {"fileproperties.type", "Type_it"},
    {"fileproperties.description", "Description_it"},
    {"fileproperties.keywords", "Keywords_it"},
    {"fileproperties.readonly", "Read Only_it"},
    {"fileproperties.shared", "Shared_it"},
    {"fileproperties.hidden", "Hidden_it"},
    {"fileproperties.isfolder", "Is Folder_it"},
    {"fileproperties.size", "Size (KB)_it"},
    {"fileproperties.currentpath", "File Path_it"},
    {"fileproperties.lastmodified", "Last Modified_it"},
    {"fileproperties.created", "Created_it"},
    {"fileproperties.contains", "Contains_it"},

    {"propertiesdialog.general", "General_it"},
    {"propertiesdialog.history", "History_it"},
    {"propertiesdialog.attributes", "Attributes_it"},
    {"propertiesdialog.sharing", "Sharing_it"},
    {"propertiesdialog.others", "Others_it"},

    {"skin.simple", "simple_it"},
    {"skin.richdemo", "richDemo_it"},

    {"error.nameisrequired", "Name is required_it"},
    {"error.invalidpathsummary", "Invalid Path._it"},
    {"error.invalidpathdetail", "Please choose the right file from the folders navigator._it"},

    {"help.help", "Help_it"},
    {"help.emailCustomerService", "Email Customer Service_it"},
    {"help.emailFileExplorer", "Email to FileExplorer.com Customer Service_it"},
    {"help.to", "To:_it"},
    {"help.attachment", "Attachment:_it"},
    {"help.name", "Name:_it"},
    {"help.emailAddress", "Email Address:_it"},
    {"help.issue", "Issue:_it"},
    {"help.cantAccessSite", "Can't access the site_it"},
    {"help.cantFindFile", "Can't find my file_it"},
    {"help.seeFileHistory", "Need to see file history_it"},
    {"help.otherQs", "Other Questions & Comments_it"},
    {"help.sendEmail", "Send Email_it"},
    {"help.cancel", "Cancel_it"},
    {"help.reset", "Reset_it"},
    {"help.submit", "Submit_it"},
    {"help.speakCustService", "Speak with Customer Service_it"},
    {"help.speakFileExplorerCustService", "Speak with a FileExplorer.com " +
    "Customer Service Representative_it"},
    {"help.available", "<b>We're available 24 hours a day, 7 days a week, " +
    "365 days a year.<br> Let us know a good time to call you, and we'll have " +
    "a customer service representative contact you.</b>_it"},
    {"help.pickDate", "Pick a date and time for us to call you_it"},
    {"help.phone", "Phone number where we should call you_it"},
    {"help.extension", " Extension_it"},
    {"help.altPhone", "Alternate phone number_it"},
    {"help.rateSite", "Rate the Site_it"},
    {"help.functionality", "Functionality_it"},
    {"help.easeOfUse", "Ease of Use_it"},
    {"help.performance", "Performance_it"},
    {"help.visualAppeal", "Visual Appeal_it"},
    {"help.newColorScheme", "If you were to choose a new color scheme for " +
    "FileExplorer, which colors would you prefer?_it"},
    {"help.primaryColor", "Primary Color_it"},
    {"help.secondaryColor", "Secondary Color_it"},
    {"help.aboutYourself", "Please tell us about yourself_it"},
    {"help.developer", "Developer_it"},
    {"help.designer", "Designer_it"},
    {"help.customer", "Customer_it"},
    {"help.manager", "Manager_it"},
    {"help.userPolls", "User Polls_it"},
    {"help.vote", "<big>Vote for your Favorite 5 File Types</big>_it"},
    {"help.availableFileTypes", "Available File Types_it"},
    {"help.top5", "Top 5 File Types (in order)_it"},
    {"help.thankYou", "Thank You_it"},
    {"help.backFileExplorer", "Back to File Explorer.com_it"},
    {"help.thankYouForContactingUs", "<big><b>Thank You</b> for submitting your " +
    "feedback.<br>We appreciate you took the time to contact us.</big>_it"},
  };
}
