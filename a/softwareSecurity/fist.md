# Software and Security Engineering

### Terminology

#### Entities

A **system** may be defined at various levels of inclusivity:
- a product or component (PC, oyster card, etc.)
- products + OS, comms, infrastructure
- the applications that run on those
- the company staff who interact with those
- the customers who use these services

A **subject** is a physical person.

A **person** might be a "legal person", i.e. a company (wtf?).

A **principal** in computer security is an entity that can be authenticated by a computer system. This may be a person, device, process/thread, etc.

#### Confidentiality, Privacy, Secrecy

- **Confidentiality** is the obligation to protect someone else's secrets, e.g. patient-doctor confidentiality.

- **Privacy** is the asimplybility to control the publicity of your own secrets.

- **Secrecy** is a technical term referring to the mechanisms which limit the number of *principals* able to access information.

#### Dependability, Reliablity, Security

- **Reliability** - The system works when we need it to, e.g. the message is recieved by the intended recipient.

- **Security** - The system is not compromisable from outside, e.g. the message cannot be read by anyone but the intended recipient.

- **Dependability** - Reliability and security combined.

#### More concepts

- **Anonymity** can mean inability to identify subjects, or not being able to link their actions. This is often about metadata.

- An object has **integrity** if it has not been altered since it was last legitimately modified.

- **Authenticity** is when you are speaking to the right principal.

- A **trusted** system or component is one that I allow to break my security policy.

#### Problems

- An **error** is either a design flaw, or a deviation from an intended state.

- A **failure** is a non-performance of the system, given some environmental conditions.

- An **accident** is an undesired, unplanned event resulting in a specified level of loss.

- A **hazard** is a set of conditions where failure can lead to an accident.

- If a **critical** system, process, or component fails, this will lead to an accident.

- **Reliability** is the probability of failure within some period of time. Usually as mean time to failure (mttf)

- **Risk** is the probability of an accident. There is likely also some **uncertainty** to this risk.

- **Safety** is freedom from accidents.

#### Documents

- A **security policy** is a succinct statement of protection goals, typically less than a page of plain language.

- A **protection profile** is a detailed statement of protection goals, typically dozens of pages of semi-formal language

- A **security target** is a detailed statement of protection goals applied to a particular system, maybe hundreds of pages of specification for both functionality and testingv

### Multi-level secure (MLS) systems

MLS systems are widely used in government. They use the threat model of an insider who is disloyal, and may leak resources. The proposed solution is to minimise the number of trusted principals, and make it harder for them to be untrustworthy.

Resources are given classifications, and principals are given clearances. The clearance must be greater or equal to the classification to allow access.
