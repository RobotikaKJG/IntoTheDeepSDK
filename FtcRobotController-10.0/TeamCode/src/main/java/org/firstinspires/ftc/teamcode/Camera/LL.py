import cv2
import numpy as np

# Global variables
testVar = 0

# Increment testVar with logic for resetting and debugging
def incrementTestVar():
    global testVar
    testVar = testVar + 1
    if testVar == 100:
        print("test")
    if testVar >= 200:
        print("print")
        testVar = 0

# Draw decorations on the image
def drawDecorations(image):
    cv2.putText(image,
        'FTC Into the Deep: Yellow Detection',
        (10, 230),
        cv2.FONT_HERSHEY_SIMPLEX,
        0.5, (0, 255, 255), 1, cv2.LINE_AA)  # Yellow text

# Main pipeline function
def runPipeline(image, llrobot):
    # Convert the image from BGR to HSV
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # Threshold for yellow color in HSV
    # Adjust these values based on lighting conditions and specimen appearance
    img_threshold = cv2.inRange(img_hsv, (20, 100, 100), (30, 255, 255))

    # Find contours in the thresholded image
    contours, _ = cv2.findContours(img_threshold, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Initialize the largest contour and data array
    largestContour = np.array([[]])
    llpython = [0, 0, 0, 0, 0, 0, 0, 0]

    if len(contours) > 0:
        # Draw all contours on the image
        cv2.drawContours(image, contours, -1, (0, 255, 255), 2)  # Yellow contours

        # Find the largest contour
        largestContour = max(contours, key=cv2.contourArea)

        # Get the bounding box for the largest contour
        x, y, w, h = cv2.boundingRect(largestContour)

        # Draw the bounding box
        cv2.rectangle(image, (x, y), (x + w, y + h), (0, 255, 255), 2)  # Yellow box

        # Update the llpython array with detection data
        llpython = [1, x, y, w, h, 0, 0, 0]

    # Increment the testVar and draw decorations
    incrementTestVar()
    drawDecorations(image)

    # Return the largest contour, the processed image, and the llpython array
    return largestContour, image, llpython
