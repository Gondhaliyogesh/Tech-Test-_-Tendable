# Tendable Tech Challenge Test Project

This project contains automated test cases for the Tendable website to verify the accessibility of top-level menus, the presence and activity of the "Request a Demo" button, and the handling of errors in the "Contact Us" form.

## Prerequisites

- Java Development Kit (JDK) installed
- Maven build tool installed
- Chrome WebDriver executable compatible with your Chrome browser version

## Setup

1. Clone this repository to your local machine.
2. Open a command prompt or terminal in the project directory.

## Running the Tests

1. Ensure that you have set up the prerequisites correctly.
2. Open the `TendableTechChallenge` class in your preferred Java IDE.
3. Modify the `baseUrl` variable to match the URL of the Tendable website if needed.
4. Run the project as a TestNG test suite.

## Test Descriptions

### Test 1: Top-Level Menus Accessibility

This test verifies the accessibility of the top-level menus on the Tendable website.

### Test 2: Request Demo Button Presence

This test checks the presence and activity of the "Request a Demo" button on specific top-level menu pages.

### Test 3: Contact Us Form Error Handling

This test navigates to the "Contact Us" section, selects "Marketing," and fills out the form, excluding the "Message" field. It then submits the form and verifies the presence of an error message.

## Test Strategy

The test strategy for this challenge involves:

1. Verifying the accessibility of top-level menus by checking if they are displayed.
2. Checking the presence and activity of the "Request a Demo" button on specific menu pages.
3. Filling out the "Contact Us" form, excluding the "Message" field, and submitting it to verify the display of an error message.

## Author
Yogesh Gondhali


