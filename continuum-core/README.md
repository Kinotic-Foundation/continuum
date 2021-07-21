# Continuum Core
Provides the core API for the Continuum framework

## Table of Contents
- [Packages](#packages)
- [Caveats](#caveats)


## Packages
1. com.kinotic.continuum.api
    * Continuum High Level API (Can be used directly by Continuum Apps)
    * Will NOT be scanned Automatically by Spring!
2. com.kinotic.continuum.api.config
   * Continuum High Level Configuration Properties Classes (Can be used directly by Continuum Apps)
   * Will be scanned Automatically by Spring!   
3. com.kinotic.continuum.core.api
    * Continuum Low Level API (Should only be used by framework implementors)
    * Will NOT be scanned Automatically by Spring!
4. com.kinotic.continuum.internal
    * Should only be used by framework implementers. 
    * Will be scanned Automatically by Spring!
    
## Caveats
