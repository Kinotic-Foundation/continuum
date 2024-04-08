import {Continuum, IServiceProxy} from '../src'

export interface ITestService {

    testMethodWithString(value: string): Promise<string>;

    testMissingMethod(): Promise<void>;

    getTestUUID(): Promise<string>;

}

class TestService implements ITestService {

    private readonly serviceProxy: IServiceProxy

    constructor() {
        this.serviceProxy = Continuum.serviceProxy('org.kinotic.continuum.gatewayserver.clienttest.ITestService')
    }

    testMethodWithString(value: string): Promise<string> {
        return this.serviceProxy.invoke('testMethodWithString', [value])
    }

    testMissingMethod(): Promise<void> {
        return this.serviceProxy.invoke('testMissingMethod')
    }

    getTestUUID(): Promise<string> {
        return this.serviceProxy.invoke('getTestUUID')
    }
}

export const TEST_SERVICE: ITestService = new TestService()
