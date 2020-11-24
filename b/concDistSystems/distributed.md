# Distributed Systems

## Introduction

As opposed to concurrent systems, distributed systems are systems spread across multiple machines in a network, which do not share an address space.

Distributed systems assume that the connections between nodes in the network may fail at any point, so we aim for *fault tolerance* - we want the system as a whole to continue working, even if some parts are faulty.

## Computer Networking

We use an abstraction of network communication, where each device is a node, and messages are passed between these nodes. This allows us to ignore the exact form that the underlying network takes.
