//
//  HelloPlugin.h
//  HelloCordova
//
//  Created by Rafael Negherbon on 3/21/14.
//
//

#import <UIKit/UIKit.h>
#import <Cordova/CDVPlugin.h>

@interface RBEWebservice : CDVPlugin

- (void) webservice:(CDVInvokedUrlCommand*) command;
- (void) getData:(CDVInvokedUrlCommand*) command;

@end
