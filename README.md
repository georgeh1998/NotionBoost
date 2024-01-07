# NotionBoost

[![Android](https://github.com/goutarouh/NotionBoost/actions/workflows/Android.yml/badge.svg)](https://github.com/goutarouh/NotionBoost/actions/workflows/Android.yml)

## About

NotionBoost 
You can easily check your Notion information on the Android home screen.

## Usage

### 1. Create an integration from ["My Integrations"](https://www.notion.so/my-integrations/) in Notion.

Please create an integration from [this page](https://www.notion.so/my-integrations/).
You can use any name for the integration. The default permissions granted to the integration include read and update and insert access to content, but NotionBoost only requires read access.

### 2. Register the created integration on your Notion page.

Please register the created Integration Secret on your Notion page. By selecting 'Add Connections' from the `...` in the top-right corner of the page, you will see the integration you created. 
For more details, please check [Notion Developers Guides](https://developers.notion.com/docs/create-a-notion-integration#give-your-integration-page-permissions)

### 3. Enter the Integration Secret in the first screen.

> [!WARNING]
> This screen does not perform Integration Secret validation. If you accidentally register an invalid Integration Secret, you will need to reinstall the app at this time.　(We plan to make it possible to change the Integration Secret within the app.)

|||
|:-:|:-:|
|<img src="./images/readme/WelcomeScreen.png" alt="WelcomeScreen" width=300 >| Please enter your Integration Key in the TextBox.<br>After that, press 'Start using Notion Boost'. |


### 4. Add a widget from the home screen.

Simply add the NotionBoost widget to your home screen.
Long-press on the home screen to display available widgets for addition. Please select the NotionBoost widget from the options.

### 5. Enter the database id.

> [!WARNING]
> If the Integration Secret or Database ID is incorrect, the error reason will be displayed as a Snackbar at the bottom of the screen after pressing 'Create Widget'.

|||
|:-:|:-:|
|<img src="./images/readme/MonthlyWidgetConfigurationScreen.png" alt="MonthlyWidgetConfigurationScreen" width=300 >| Please enter the DatabaseId of the database <br>you want to display in the widget in the TextBox.<br>After that, press 'Create Widget'. |

### 6. Check the widgets.
