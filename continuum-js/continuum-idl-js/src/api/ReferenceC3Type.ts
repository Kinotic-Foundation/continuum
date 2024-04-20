import {BaseC3Type} from '@/api/BaseC3Type'

/**
 * A simple Type to allow referencing other components in the specification, internally and externally.
 * Created by navid on 2023-4-13.
 */
export class ReferenceC3Type extends BaseC3Type {

    /**
     * The {@link ObjectC3Type#getQualifiedName()} to the schema being referenced
     */
    public qualifiedName: string | null = null

    constructor() {
        super('ref');
    }
}
