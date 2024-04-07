import {describe, expect, it, beforeAll, afterAll} from 'vitest'
import {ConnectionInfo, Continuum, ConnectedInfo} from '../src'
import {WebSocket} from 'ws'
import {logFailure, validateConnectedInfo, initContinuumGateway} from './TestHelper'
import {TEST_SERVICE} from './ITestService'
import {StartedTestContainer} from 'testcontainers'
import {NON_EXISTENT_SERVICE} from './INonExistentService'

// This is required when running Continuum from node
Object.assign(global, { WebSocket})

describe('Continuum RPC Tests', () => {

    let connectionInfo: ConnectionInfo = new ConnectionInfo()
    let container: StartedTestContainer

    beforeAll(async () => {
        container = await initContinuumGateway()
        connectionInfo.host = container.getHost()
        connectionInfo.port = container.getMappedPort(58503)
        connectionInfo.maxConnectionAttempts = 3
        // connectionInfo.host = '127.0.0.1'
        // connectionInfo.port = 58503
        connectionInfo.connectHeaders = {login: 'guest', passcode: 'guest'}
        console.log(`Continuum Gateway running at ${connectionInfo.host}:${connectionInfo.port}`)

        let connectedInfo: ConnectedInfo = await logFailure(Continuum.connect(connectionInfo), 'Failed to connect to Continuum Gateway')
        let serverInfo = Continuum.eventBus.serverInfo
        validateConnectedInfo(connectedInfo)
    }, 1000 * 60 * 10) // 10 minutes

    afterAll(async () =>{
        await expect(Continuum.disconnect()).resolves.toBeUndefined()
        await container.stop()
    })


    it('should allow context switching', async () =>{
        await expect(TEST_SERVICE.testMethodWithString("Bob")).resolves.toBe("Hello Bob")
    })


})
