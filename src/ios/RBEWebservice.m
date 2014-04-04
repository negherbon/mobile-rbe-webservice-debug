//
//  HelloPlugin.m
//  HelloCordova
//
//  Created by Rafael Negherbon on 3/21/14.
//
//

#import "RBEWebservice.h"

@interface RBEWebservice ()

@end

@implementation RBEWebservice


- (void) webservice:(CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;
    NSString *ret = [self getJsonDataFromWeb];
    [self createFileInDocument:@"data.json" withContent:ret];
    // Monta a resposta para o javascript
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:ret];
    // Manda o retorno para o javascript
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSString*) getDocumentsDirectory{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return documentsDirectory;
}

- (void) getData:(CDVInvokedUrlCommand *)command{
	NSLog(@"GetData");
    CDVPluginResult* pluginResult = nil;
    NSString* ret = [self getJsonDataFromDirectory:@"data.json"];
    // Monta a resposta para o javascript
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:ret];
    // Manda o retorno para o javascript
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSString*) getJsonDataFromWeb{
    NSURL *url = [NSURL URLWithString:@"http://www.rbenergia.com.br/ws/wsrbe.php"];
    NSData *data = [NSData dataWithContentsOfURL:url];
    NSString *ret = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    return ret;
}

- (NSString*) getJsonDataFromDirectory:(NSString*) fileName{
    NSString* documentsDirectory = [self getDocumentsDirectory];
    NSString* filePath = [documentsDirectory stringByAppendingPathComponent:fileName];
    
    return [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
}

- (void) createFileInDocument: (NSString *) fileName withContent:(NSString*) content{
    NSString *documentsDirectory = [self getDocumentsDirectory];
    
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:fileName];
    NSLog(@"file %@ ", filePath);
    
    NSFileManager* fileManager = [NSFileManager defaultManager];
    
    if ([fileManager fileExistsAtPath:filePath] == NO){
        [self createDocument:filePath];
        
        if ([fileManager fileExistsAtPath:filePath] == YES){
            [self updateDocument:filePath withContent:content];
        }
        
    }else{
        [self updateDocument:filePath withContent:content];
    }
    
    //[self updateDocument:filePath withContent:content];
}

- (void) createDocument:(NSString*) filePath{
    NSFileManager* fileManager = [NSFileManager defaultManager];
    // Se nao conseguiu criar o arquivo
    if (![fileManager createFileAtPath:filePath contents:nil attributes:nil]){
        NSLog(@"Create file returned NO!");
    }
}

- (void) updateDocument:(NSString*) filePath withContent:(NSString*) content{
    NSLog(@"Update data from data.json!");
    [content writeToFile:filePath atomically:YES encoding:NSUTF8StringEncoding error:nil];
}


@end
