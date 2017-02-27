;_ChromeCreate() - Create Chrome Window
;_ChromeGetObjByName() - Get's a WebPage Name (Similar to id)
;_ChromeAction - Does an Action on Id/Name or Web Object
;_ChromePropertySet() - Sets a property to an Object - innerText

#include <Chrome.au3>

$oChrome = _ChromeCreate("http://wc2qa.ps.com/Payment/Make?accountNumber=20404908&AccountOrderItemIds=39037830")

